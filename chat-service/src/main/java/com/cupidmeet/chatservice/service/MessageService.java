package com.cupidmeet.chatservice.service;

import com.cupidmeet.chatservice.domain.dto.CreateMessageRequest;
import com.cupidmeet.chatservice.domain.entity.Message;

public interface MessageService {

    /**
     * Сохранить сообщение
     *
     * @param messageRequest запрос на создание сообщения
     */
    Message saveMessage(CreateMessageRequest messageRequest);
}
