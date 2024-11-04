package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    /**
     * Получение чатов по идентификатору участника.
     *
     * @param participantId идентификатор участника
     * @return список чатов
     */
    Set<Chat> findByParticipantsId(UUID participantId);
}
