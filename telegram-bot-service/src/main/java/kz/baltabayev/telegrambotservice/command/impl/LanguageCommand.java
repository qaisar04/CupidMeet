package kz.baltabayev.telegrambotservice.command.impl;

import kz.baltabayev.telegrambotservice.command.Command;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.selector.LanguageSelector;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Component
@RequiredArgsConstructor
public class LanguageCommand implements Command {

    private final MessageSender sender;
    private final BotStateService botStateService;

    @Override
    public void execute(Message message) {
        long userId = message.getChatId();
        SendMessage languageSelectionMessage = LanguageSelector.createLanguageSelectionMessage(userId);
        botStateService.save(userId, BotState.SELECT_LANGUAGE);
        sender.sendMessage(languageSelectionMessage);
    }
}