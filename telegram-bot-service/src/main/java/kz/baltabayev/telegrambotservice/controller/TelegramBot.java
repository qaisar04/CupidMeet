package kz.baltabayev.telegrambotservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    @Value("${bot.token.api}")
    private String token;

    public TelegramBot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

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
//            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    private void handleMessage(Message message) {
        if (message.hasText()) {
            String messText = message.getText();
            if (messText.equals("/exit")) {
//                handleExitCommand(messText.getUserId());
                return;
            }

            if (messText.equals("/start")) {
                String username = Optional.ofNullable(message.getFrom().getUserName())
                        .orElse(message.getFrom().getFirstName());
                startCommand(message.getChatId(), username);
                return;

            }

//            handleTextMessage(message.getText(), message.getFrom().getId());
        } else {
            sendMessage(message.getChatId(), "i'm not supported this message type");
        }
    }

    public void startCommand(long userId, String name) {
        sendMessage(userId, "HELLO!");
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        message.enableMarkdown(true);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        System.out.println("Registered bot running state is: " + botSession.isRunning());
    }
}
