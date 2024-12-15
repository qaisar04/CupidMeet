package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.ChatAddParticipantsRequest;
import com.cupidmeet.chatservice.domain.dto.ChatParticipantResponse;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;

import java.util.List;
import java.util.UUID;

public interface ChatParticipantService {

    /**
     * Получить список участников чата.
     *
     * @param chatId идентификатор чата
     * @return список участников чата
     */
    List<ChatParticipantResponse> getParticipants(UUID chatId);

    /**
     * Добавление участников в чат.
     *
     * @param chatId  идентификатор чата
     * @param request запрос на добавление участников
     */
    void addParticipants(UUID chatId, ChatAddParticipantsRequest request);

    /**
     * Обновление роли участника.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @param role роль
     */
    void updateParticipantRole(UUID chatId, UUID userId, ParticipantRole role);

    /**
     * Удаление участника из чата.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     */
    void removeParticipant(UUID chatId, UUID userId);

    /**
     * Проверка на администратора.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return признак администратора
     */
    boolean isAdmin(UUID chatId, UUID userId);
}
