package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, UUID> {

    /**
     * Найти участника чата по идентификатору чата и идентификатору пользователя.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return участник чата
     */
    Optional<ChatParticipant> findByChatIdAndUserId(UUID chatId, UUID userId);
}
