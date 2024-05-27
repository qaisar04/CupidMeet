package kz.baltabayev.telegrambotservice.command.impl;

import kz.baltabayev.telegrambotservice.client.UserDetailsServiceClient;
import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.model.payload.UserCreateRequest;
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

    @Override
    public void execute(Message message) {
        String username = Optional.ofNullable(message.getFrom().getUserName())
                .orElse(message.getFrom().getFirstName());
        long userId = message.getChatId();
        userDetailsServiceClient.createUser(new UserCreateRequest(userId, username));
        sender.sendMessage(userId, "HELLO!");
    }
}
