package kz.baltabayev.telegrambotservice.handler;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public abstract class BotStateHandler {

    private final BotStateService botStateService;

    public abstract void handle(Message message);

    public BotState getNextState(Long userId) {
        return botStateService.get(userId);
    }

    protected void setNextState(Long userId, BotState botState) {
        botStateService.save(userId, botState);
    }
}