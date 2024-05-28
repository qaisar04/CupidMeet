package kz.baltabayev.telegrambotservice.handler;

import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DialogHandler {

    @Setter
    protected UserState userState;
    protected MessageSender sender;
    protected BotStateService botStateService;

    @Autowired
    public void setMessageSender(MessageSender sender, BotStateService botStateService) {
        this.sender = sender;
        this.botStateService = botStateService;
    }

    public void handleInput(String input, Long userId) {
        BotState state = botStateService.get(userId);
        switch (state) {
            case NONE:
                askFirstQuestion(sender);
                botStateService.save(userId, BotState.AWAITING_FIRST);
                break;
            case AWAITING_FIRST:
                handleFirstInput(input);
                askSecondQuestion(sender);
                botStateService.save(userId, BotState.AWAITING_SECOND);
                break;
            case AWAITING_SECOND:
                handleSecondInput(input);
                completeDialog();
                botStateService.save(userId, BotState.DEFAULT);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }
    }

    protected abstract void askFirstQuestion(MessageSender sender);

    protected abstract void handleFirstInput(String input);

    protected abstract void askSecondQuestion(MessageSender sender);

    protected abstract void handleSecondInput(String input);

    protected abstract void completeDialog();
}