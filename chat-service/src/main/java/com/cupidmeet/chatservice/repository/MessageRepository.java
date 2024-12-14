package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.Message;
import com.cupidmeet.chatservice.domain.enumeration.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    /**
     * Получить список сообщений для определенного чата с сортировкой по времени.
     *
     * @param chat чат
     * @return список сообщений
     */
    Page<Message> findByChatOrderByTimestampDesc(Chat chat, Pageable pageable);

    /**
     * Подсчет сообщений по идентификатору чата, идентификатору пользователя и статусу.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @param status статус сообщения
     * @return количество сообщений
     */
    long countByChatIdAndUserIdAndStatus(UUID chatId, UUID userId, MessageStatus status);
}
