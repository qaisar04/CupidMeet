package com.cupidmeet.chatservice.repository;

import com.cupidmeet.chatservice.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    /**
     * Получить список сообщений для определенного чата.
     *
     * @param chatId идентификатор чата
     * @return список сообщений
     */
    List<Message> findAllByChatId(UUID chatId);
}
