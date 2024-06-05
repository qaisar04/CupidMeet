package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class AwaitingNameHandler extends BotStateHandler {

    private final UserStateService userStateService;
    private final MessageSender messageSender;

    @Autowired
    public AwaitingNameHandler(BotStateService botStateService, UserStateService userStateService, MessageSender messageSender) {
        super(botStateService);
        this.userStateService = userStateService;
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Message message) {
        Long userId = message.getChatId();
        if (message.hasText()) {
            userStateService.updateName(userId, message.getText());
            setNextState(userId, BotState.AWAITING_AGE);
        } else {
            messageSender.sendMessage(userId, "Пожалуйста, введите имя.");
        }
    }
}