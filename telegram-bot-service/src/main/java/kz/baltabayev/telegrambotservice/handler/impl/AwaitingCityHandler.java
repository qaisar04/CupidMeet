package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.client.LocationDataServiceClient;
import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
public class AwaitingCityHandler extends BotStateHandler {

    private final UserStateService userStateService;
    private final MessageSender messageSender;
    private final LocationDataServiceClient locationDataServiceClient;

    @Autowired
    public AwaitingCityHandler(BotStateService botStateService, UserStateService userStateService, MessageSender messageSender, LocationDataServiceClient locationDataServiceClient) {
        super(botStateService);
        this.userStateService = userStateService;
        this.messageSender = messageSender;
        this.locationDataServiceClient = locationDataServiceClient;
    }

    @Override
    public void handle(Message message) {
        Long userId = message.getChatId();
        if (message.hasText()) {
            userStateService.updateCity(userId, message.getText());
            setNextState(userId, BotState.COMPLETED);
        } else if (message.hasLocation()) {
            handleLocation(message);
        } else {
            messageSender.sendMessage(userId, "Пожалуйста, введите ваш город.");
        }
    }

    private void handleLocation(Message message) {
        //todo handle with locationDataServiceClient
    }
}