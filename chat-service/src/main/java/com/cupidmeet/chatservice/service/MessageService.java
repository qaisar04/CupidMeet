package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.MessageResponse;
import com.cupidmeet.chatservice.domain.dto.MessageSendRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {

    /**
     * Отправка сообщения.
     *
     * @param request запрос на отправку сообщения
     * @return ответ с отправленным сообщением
     */
    MessageResponse sendMessage(MessageSendRequest request);

    /**
     * Получение сообщения по идентификатору чата.
     *
     * @param chatId идентификатор чата
     * @return сообщение
     */
    Page<MessageResponse> getMessages(UUID chatId, Pageable pageable);

    /**
     * Удаление сообщения.
     *
     * @param messageId идентификатор сообщения
     */
    void deleteMessage(UUID messageId);

    /**
     * Пометка сообщения как прочитанного.
     *
     * @param messageId идентификатор сообщения
     * @param userId идентификатор пользователя
     */
    void markMessageAsRead(UUID messageId, UUID userId);

    /**
     * Подсчет непрочитанных сообщений.
     *
     * @param chatId идентификатор чата
     * @param userId идентификатор пользователя
     * @return количество непрочитанных сообщений
     */
    long countUnreadMessages(UUID chatId, UUID userId);

    UUID getChatIdByMessageId(UUID messageId);
}
