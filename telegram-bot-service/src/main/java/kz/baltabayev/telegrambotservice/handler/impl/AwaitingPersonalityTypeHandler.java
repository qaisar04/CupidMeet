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
public class AwaitingPersonalityTypeHandler extends BotStateHandler {

    private final UserStateService userStateService;
    private final MessageSender messageSender;

    @Autowired
    public AwaitingPersonalityTypeHandler(BotStateService botStateService, MessageSender messageSender, UserStateService userStateService) {
        super(botStateService);
        this.messageSender = messageSender;
        this.userStateService = userStateService;
    }

    @Override
    public void handle(Message message) {
        Long userId = message.getChatId();
        if (message.hasText()) {
            userStateService.updateMbti(userId, message.getText());
            setNextState(userId, BotState.AWAITING_BIO);
        } else {
            messageSender.sendMessage(userId, "Пожалуйста, выберите ваш MBTI тип.");
        }
    }
}