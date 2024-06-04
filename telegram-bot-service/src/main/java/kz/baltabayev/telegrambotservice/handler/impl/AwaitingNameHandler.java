package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class AwaitingNameHandler implements BotStateHandler {

    private final UserStateService userStateService;
    private final BotStateService botStateService;
    private final MessageSender messageSender;

    private BotState nextState;

    @Override
    public void handle(Message message, UserState userState) {
        if (message.hasText()) {
            userState.setName(message.getText());
            userStateService.save(userState);
            nextState = BotState.AWAITING_AGE;
        } else {
            messageSender.sendMessage(userState.getUserId(), "Пожалуйста, введите имя.");
        }
    }

    @Override
    public BotState getNextState() {
        return nextState;
    }
}