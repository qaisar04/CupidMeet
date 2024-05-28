package kz.baltabayev.telegrambotservice.handler;

import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExtendedDialogHandler extends DialogHandler {

    private final UserStateService userStateService;

    @Autowired
    public ExtendedDialogHandler(MessageSender sender, BotStateService botStateService, UserStateService userStateService) {
        this.botStateService = botStateService;
        this.sender = sender;
        this.userStateService = userStateService;
    }

    @Override
    protected void askFirstQuestion(MessageSender sender) {
        Long userId = userState.getUserId();
        sender.sendMessage(userId, "Please enter your name:");
        botStateService.save(userId, BotState.AWAITING_FIRST);
    }

    @Override
    protected void handleFirstInput(String name) {
        userState.setName(name);
        userStateService.save(userState);
    }

    @Override
    protected void askSecondQuestion(MessageSender sender) {
        Long userId = userState.getUserId();
        sender.sendMessage(userId, "Please enter your age:");
        botStateService.save(userId, BotState.AWAITING_SECOND);
    }

    @Override
    protected void handleSecondInput(String age) {
        userState.setAge(Integer.parseInt(age));
        userStateService.save(userState);
    }

    @Override
    protected void completeDialog() {
        sender.sendMessage(userState.getUserId(), "Thank you! Processing your data...");
    }
}