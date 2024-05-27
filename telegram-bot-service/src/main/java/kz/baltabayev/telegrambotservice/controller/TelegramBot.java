package kz.baltabayev.telegrambotservice.controller;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.command.factory.CommandFactory;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;
    private final CommandFactory commandFactory;
    private final MessageSender messageSender;

    @Override
    public String getBotToken() {
        return "7411579054:AAGJiFtdlJ7PGSw2dFsDjI14EvK94rF0QwY";
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
           //TODO
        }
    }

    private void handleMessage(Message message) {
        if (message.hasText()) {
            String messText = message.getText();

            Command command = commandFactory.getCommand(messText);
            if (command != null) {
                command.execute(message);
            } else {
                messageSender.sendMessage(message.getChatId(), "I'm not supported this message type");
            }
        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}