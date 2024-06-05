package kz.baltabayev.telegrambotservice.command.impl;

import kz.baltabayev.telegrambotservice.client.UserDetailsServiceClient;
import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.service.LocalizationService;
import kz.baltabayev.telegrambotservice.service.UserStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final MessageSender sender;
    private final UserStateService userStateService;
    private final LocalizationService localizationService;
    private final UserDetailsServiceClient userDetailsServiceClient;

    @Override
    public void execute(Message message) {
        long userId = message.getChatId();
        String username = Optional.ofNullable(message.getFrom().getUserName())
                .orElse(message.getFrom().getFirstName());
//        userDetailsServiceClient.create(new UserCreateRequest(userId, username));

        UserState state = userStateService.get(userId);
        if (!state.isProfileComplete()) {
            sender.sendMessage(userId, localizationService.getMessage("welcome", state.getLanguage()));
        } else {
            sender.sendMessage(userId, "Welcome back, " + username + "!");
        }
    }
}