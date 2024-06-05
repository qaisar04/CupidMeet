package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Comparator;
import java.util.List;

@Component
public class AwaitingImageHandler extends BotStateHandler {

    private final MessageSender messageSender;

    @Autowired
    public AwaitingImageHandler(BotStateService botStateService, MessageSender messageSender) {
        super(botStateService);
        this.messageSender = messageSender;
    }

    @Override
    public void handle(Message message) {
        Long userId = message.getChatId();
        if (message.hasPhoto()) {
            List<PhotoSize> photos = message.getPhoto();
            PhotoSize largestPhoto = photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null);

            if (largestPhoto != null) {
                String photoFileId = largestPhoto.getFileId();
                //todo

                setNextState(userId, BotState.AWAITING_PERSONALITY_TYPE);
            } else {
                messageSender.sendMessage(userId, "Не удалось обработать вашу фотографию. Попробуйте еще раз.");
            }
        } else {
            messageSender.sendMessage(userId, "Пожалуйста, отправьте фотографию.");
        }
    }
}