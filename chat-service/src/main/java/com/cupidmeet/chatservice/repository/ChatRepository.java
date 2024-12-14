package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {

    /**
     * Получение чатов по идентификатору участника.
     *
     * @param userId идентификатор участника
     * @return список чатов
     */
    Page<Chat> findByParticipants_UserId(UUID userId, Pageable pageable);
}
