package kz.baltabayev.telegrambotservice.controller;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.command.factory.CommandFactory;
import kz.baltabayev.telegrambotservice.handler.DialogHandler;
import kz.baltabayev.telegrambotservice.handler.factory.DialogHandlerFactory;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
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
    private final DialogHandlerFactory dialogHandlerFactory;
    private final MessageSender messageSender;
    private final BotStateService botStateService;
    private final UserStateService userStateService;

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
        if (message.hasText()) {
            long userId = message.getChatId();
            String messText = message.getText();

            if (messText.startsWith("/")) {
                Command command = commandFactory.getCommand(messText);
                if (command != null) {
                    command.execute(message);
                }
            }

            UserState userState = userStateService.get(userId);
            BotState botState = botStateService.get(userId);

            DialogHandler handler = dialogHandlerFactory.getHandler(botState);
            if (handler != null && !botState.equals(BotState.DEFAULT)) {
                handler.setUserState(userState);
                handler.handleInput(messText, userId);
            } else {
                messageSender.sendMessage(userId, "I'm not supported this message type");
            }
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        long userId = callbackQuery.getMessage().getChatId();
        String data = callbackQuery.getData();

        UserState userState = userStateService.get(userId);

        if ("lang_en".equals(data)) {
            userState.setLanguage("en");
            messageSender.sendMessage(userId, "You have selected English.");
        } else if ("lang_ru".equals(data)) {
            userState.setLanguage("ru");
            messageSender.sendMessage(userId, "Вы выбрали русский язык.");
        } else if ("lang_kz".equals(data)) {
            userState.setLanguage("ru");
            messageSender.sendMessage(userId, "Сіз қазақ тілін тандадыныз.");
        }

        userStateService.save(userState);
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}