package kz.baltabayev.telegrambotservice.util;

import kz.baltabayev.telegrambotservice.keyboard.factory.KeyboardFactory;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageSender {

    private final TelegramClient telegramClient;
    private final KeyboardFactory keyboardFactory;

    public void sendMessage(long userId, String text) {
        log.info("Sending message to userId: {}", userId);
        SendMessage message = SendMessage.builder()
                .chatId(userId)
                .text(text)
                .build();

        message.enableMarkdown(true);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message to userId: {} | error {}", userId, e.getLocalizedMessage());
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            log.error("Failed to send message");
        }
    }

    public void sendKeyboard(Long chatId, String text, BotState state) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboardFactory.getKeyboard(state))
                .build();

        sendMessage(message);
    }
}