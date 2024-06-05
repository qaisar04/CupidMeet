package kz.baltabayev.telegrambotservice.controller;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.command.factory.CommandFactory;
import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.handler.factory.BotStateHandlerFactory;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.model.types.Language;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.LocalizationService;
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
    private final LocalizationService localizationService;

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
        UserState userState = userStateService.get(userId);
        BotState botState = botStateService.get(userId);

        if (botState.equals(BotState.NONE)) {
            userStateService.init(userId);
            botStateService.save(userId, BotState.AWAITING_NAME);
            messageSender.sendMessage(userId, localizationService.getMessage("prompt.awaiting_name", userState.getLanguage()));
            return;
        }

        if (message.hasText() && message.getText().startsWith("/")) {
            handleCommand(message);
            return;
        }

        if (!botState.equals(BotState.DEFAULT)) {
            handleBotState(botState, message, userState, userId);
        }
    }

    private void handleCommand(Message message) {
        Command command = commandFactory.getCommand(message.getText());
        if (command != null) {
            command.execute(message);
        } else {
            log.warn("Unknown command: {}", message.getText());
        }
    }

    private void handleBotState(BotState botState, Message message, UserState userState, Long userId) {
        BotStateHandler handler = botStateHandlerFactory.getHandler(botState);
        if (handler != null) {
            handler.handle(message);
            BotState nextState = handler.getNextState(userId);
            promptNextState(userId, nextState, userState.getLanguage());
        } else {
            log.error("No handler found for bot state: {}", botState);
        }
    }

    private void promptNextState(Long userId, BotState nextState, String languageCode) {
        String messageKey = switch (nextState) {
            case AWAITING_PHOTO -> "prompt.awaiting_photo";
            case AWAITING_AGE -> "prompt.awaiting_age";
            case AWAITING_GENDER -> "prompt.awaiting_gender";
            case AWAITING_BIO -> "prompt.awaiting_bio";
            case AWAITING_PERSONALITY_TYPE -> "prompt.awaiting_personality_type";
            case AWAITING_CITY -> "prompt.awaiting_city";
            case COMPLETED -> "prompt.completed";
            default -> null;
        };

        if (messageKey != null) {
            messageSender.sendMessage(userId, localizationService.getMessage(messageKey, languageCode));
        } else {
            log.warn("No prompt message for bot state: {}", nextState);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();
        UserState userState = userStateService.get(userId);

        Language language = Language.fromCode(data);
        if (language != null) {
            userState.setLanguage(language.getLanguage());
            userStateService.save(userState);
            messageSender.sendMessage(userId, language.getSelectionMessage());

            BotState botState = botStateService.get(userId);
            promptNextState(userId, botState, userState.getLanguage());
        } else {
            messageSender.sendMessage(userId, "Unsupported language selection.");
        }
    }
}