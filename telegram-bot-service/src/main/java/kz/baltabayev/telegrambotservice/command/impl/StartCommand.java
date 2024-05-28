package kz.baltabayev.telegrambotservice.command.impl;

import kz.baltabayev.telegrambotservice.client.UserDetailsServiceClient;
import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.payload.UserCreateRequest;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final UserDetailsServiceClient userDetailsServiceClient;
    private final MessageSender sender;
    private final UserStateService userStateService;

    @Override
    public void execute(Message message) {
        long userId = message.getChatId();
        String username = Optional.ofNullable(message.getFrom().getUserName())
                .orElse(message.getFrom().getFirstName());
        userDetailsServiceClient.createUser(new UserCreateRequest(userId, username));

        UserState state = userStateService.get(userId);
        if (!state.isProfileComplete()) {
            userStateService.init(userId);
            sender.sendMessage(userId, "Welcome! Let's start filling out your profile. Please enter your name:");
        } else {
            sender.sendMessage(userId, "Welcome back, " + username + "!");
        }
    }
}