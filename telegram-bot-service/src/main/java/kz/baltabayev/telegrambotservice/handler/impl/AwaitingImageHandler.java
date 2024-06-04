package kz.baltabayev.telegrambotservice.handler.impl;

import kz.baltabayev.telegrambotservice.handler.BotStateHandler;
import kz.baltabayev.telegrambotservice.model.entity.UserState;
import kz.baltabayev.telegrambotservice.model.types.BotState;
import kz.baltabayev.telegrambotservice.service.BotStateService;
import kz.baltabayev.telegrambotservice.util.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AwaitingImageHandler implements BotStateHandler {

    private final BotStateService botStateService;
    private final MessageSender messageSender;

    private BotState nextState;

    @Override
    public void handle(Message message, UserState userState) {
        if (message.hasPhoto()) {
            List<PhotoSize> photos = message.getPhoto();
            PhotoSize largestPhoto = photos.stream()
                    .max(Comparator.comparing(PhotoSize::getFileSize))
                    .orElse(null);

            if (largestPhoto != null) {
                String photoFileId = largestPhoto.getFileId();
                // Сохранение фотографии или её идентификатора
                // userStateService.save(userState);

                nextState = BotState.AWAITING_PERSONALITY_TYPE;
            } else {
                messageSender.sendMessage(userState.getUserId(), "Не удалось обработать вашу фотографию. Попробуйте еще раз.");
            }
        } else {
            messageSender.sendMessage(userState.getUserId(), "Пожалуйста, отправьте фотографию.");
        }
    }

    @Override
    public BotState getNextState() {
        return nextState;
    }
}