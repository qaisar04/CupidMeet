package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.CreateChatRequest;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;

import java.util.Set;
import java.util.UUID;

/**
 * Интерфейс сервиса чата.
 */
public interface ChatService {

    /**
     * Создание чата.
     *
     * @param request запрос на создание чата
     */
    Chat create(CreateChatRequest request);

    /**
     * Получение чата по идентификатору пользователя.
     *
     * @param participantId идентификатор пользователя
     * @return список чатов
     */
    Set<Chat> getChatsByParticipant(UUID participantId);

    /**
     * Получение участников чата.
     *
     * @param chatId идентификатор чата
     * @return участники чата
     */
    Set<ChatParticipant> getChatParticipants(UUID chatId);
}
