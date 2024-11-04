package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.CreateMessageRequest;
import com.cupidmeet.chatservice.domain.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    /**
     * Сохранить сообщение.
     *
     * @param messageRequest запрос на создание сообщения
     */
    Message saveMessage(CreateMessageRequest messageRequest);

    /**
     * Получить список сообщений для определенного чата.
     *
     * @param chatId идентификатор чата
     * @return список сообщений
     */
    List<Message> getChatMessages(UUID chatId);
}
