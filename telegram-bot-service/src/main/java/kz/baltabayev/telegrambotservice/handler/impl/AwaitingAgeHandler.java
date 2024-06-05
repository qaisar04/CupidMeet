package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class AwaitingAgeHandler extends BotStateHandler {

    private final UserStateService userStateService;
    private final MessageSender messageSender;

    public AwaitingAgeHandler(BotStateService botStateService, UserStateService userStateService, MessageSender messageSender) {
        super(botStateService);
        this.userStateService = userStateService;
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Message message) {
        Long userId = message.getChatId();
        if (message.hasText()) {
            try {
                userStateService.updateAge(userId, Integer.parseInt(message.getText()));
                setNextState(userId, BotState.AWAITING_GENDER);
            } catch (NumberFormatException e) {
                messageSender.sendMessage(userId, "Пожалуйста, введите корректный возраст.");
            }
        } else {
            messageSender.sendMessage(userId, "Пожалуйста, введите возраст.");
        }
    }
}