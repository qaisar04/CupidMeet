package kz.baltabayev.telegrambotservice.controller;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.command.factory.CommandFactory;
import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.handler.factory.BotStateHandlerFactory;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.model.types.Language;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final CommandFactory commandFactory;
    private final MessageSender messageSender;
    private final UserStateService userStateService;
    private final BotStateService botStateService;
    private final BotStateHandlerFactory botStateHandlerFactory;

    @Value("${bot.token.api}")
    private String token;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            handleMessage(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleMessage(Message message) {
        Long userId = message.getChatId();
        UserState userState = userStateService.get(userId); // автоматически инициализируется, если null
        BotState botState = botStateService.get(userId);

        if (botState == null) {
            botState = BotState.AWAITING_NAME;
            botStateService.save(userId, botState);
            messageSender.sendMessage(userId, "Как Вас зовут?");
            return;
        }

        if (message.hasText() && message.getText().startsWith("/")) {
            handleCommand(message, message.getText());
        } else {
            handleBotState(botState, message, userState, userId);
        }
    }

    private void handleCommand(Message message, String messText) {
        Command command = commandFactory.getCommand(messText);
        if (command != null) {
            command.execute(message);
        } else {
            log.warn("Unknown command: {}", messText);
        }
    }

    private void handleBotState(BotState botState, Message message, UserState userState, Long userId) {
        BotStateHandler handler = botStateHandlerFactory.getHandler(botState);
        if (handler != null) {
            handler.handle(message, userState);
            BotState nextState = handler.getNextState();
            if (nextState != null) {
                botStateService.save(userId, nextState);
                promptNextState(userId, nextState);
            }
        } else {
            log.error("No handler found for bot state: {}", botState);
        }
    }

    private void promptNextState(Long userId, BotState nextState) {
        switch (nextState) {
            case AWAITING_PHOTO:
                messageSender.sendMessage(userId, "Пожалуйста, отправьте свою фотографию.");
                break;
            case AWAITING_AGE:
                messageSender.sendMessage(userId, "Сколько вам лет?");
                break;
            case AWAITING_GENDER:
                messageSender.sendKeyboard(userId, "Какой у вас пол? Выберите подходящий вариант ниже:", BotState.SELECT_GENDER);
                break;
            case AWAITING_BIO:
                messageSender.sendMessage(userId, "Расскажите немного о себе.");
                break;
            case AWAITING_PERSONALITY_TYPE:
                messageSender.sendMessage(userId, "Отправьте ваш MBTI тип.");
                break;
            case AWAITING_CITY:
                messageSender.sendMessage(userId, "Отправьте свой город или геопозицию.");
                break;
            case COMPLETED:
                messageSender.sendMessage(userId, "Анкета заполнена. Спасибо за вашу информацию.");
                break;
            default:
                log.warn("No prompt message for bot state: {}", nextState);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        UserState userState = userStateService.get(userId);

        Language language = Language.fromCode(data);
        if (language != null) {
            userState.setLanguage(language.getCode());
            messageSender.sendMessage(userId, language.getSelectionMessage());
        } else {
            messageSender.sendMessage(userId, "Unsupported language selection.");
        }

        userStateService.save(userState);
    }
}