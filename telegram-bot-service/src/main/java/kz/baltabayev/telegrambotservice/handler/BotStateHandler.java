package kz.baltabayev.telegrambotservice.handler;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import org.telegram.telegrambots.meta.api.objects.message.Message;

public interface BotStateHandler {

    void handle(Message message, UserState userState);

    BotState getNextState();
}