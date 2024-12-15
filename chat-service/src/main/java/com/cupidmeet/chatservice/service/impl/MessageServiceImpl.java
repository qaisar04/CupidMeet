package com.cupidmeet.chatservice.service.impl;

import com.cupidmeet.chatservice.domain.dto.MessageResponse;
import com.cupidmeet.chatservice.domain.dto.MessageSendRequest;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.Message;
import com.cupidmeet.chatservice.domain.enumeration.MessageStatus;
import com.cupidmeet.chatservice.mapper.MessageMapper;
import com.cupidmeet.chatservice.repository.ChatRepository;
import com.cupidmeet.chatservice.repository.MessageRepository;
import com.cupidmeet.chatservice.service.MessageService;
import com.cupidmeet.chatservice.service.UserService;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.cupidmeet.chatservice.message.Messages.NOT_FOUND;
import static com.cupidmeet.chatservice.message.Messages.VALIDATION_ERROR;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final UserService userService;

    @Override
    public MessageResponse sendMessage(MessageSendRequest request) {
        Chat chat = getChatById(request.getChatId());

        UUID currentUserId = userService.getCurrentId().orElseThrow(
                () -> new CustomRuntimeException(VALIDATION_ERROR, "Идентификатор текущего пользователя", "не найден")
        );

        Message message = Message.builder()
                .chat(chat)
                .userId(currentUserId)
                .content(request.getContent())
                .build();

        Message savedMessage = messageRepository.save(message);
        return messageMapper.toResponse(savedMessage);
    }

    @Override
    public long countUnreadMessages(UUID chatId, UUID userId) {
        return messageRepository.countByChatIdAndUserIdAndStatus(chatId, userId, MessageStatus.SENT);
    }

    @Override
    public Page<MessageResponse> getMessages(UUID chatId, Pageable pageable) {
        Chat chat = getChatById(chatId);
        return messageRepository.findByChatOrderByTimestampDesc(chat, pageable)
                .map(messageMapper::toResponse);
    }

    @Override
    public void deleteMessage(UUID messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Сообщение", "идентификатором", messageId));

        messageRepository.delete(message);
    }

    @Override
    public void markMessageAsRead(UUID messageId, UUID userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Сообщение", "идентификатором", messageId));

        if (!message.getUserId().equals(userId)) {
            throw new CustomRuntimeException(VALIDATION_ERROR, "userId", "не соответствует отправителю сообщения");
        }

        message.setStatus(MessageStatus.READ);
        messageRepository.save(message);
    }

    @Override
    public UUID getChatIdByMessageId(UUID messageId) {
        return messageRepository.findById(messageId)
                .map(message -> message.getChat().getId())
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Сообщение", "идентификатором", messageId));
    }

    private Chat getChatById(UUID chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new CustomRuntimeException(NOT_FOUND, "Чат", "идентификатором", chatId));
    }
}
