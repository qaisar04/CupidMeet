package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.entity.ChatParticipant;

import java.util.UUID;

public interface ChatParticipantService {

    /**
     * Сохранить участника чата.
     *
     * @param chatParticipant участник чата
     */
    void save(ChatParticipant chatParticipant);

    /**
     * Отключить участника чата.
     *
     * @param participantId идентификатор участника
     */
    void disconnect(UUID participantId);
}
