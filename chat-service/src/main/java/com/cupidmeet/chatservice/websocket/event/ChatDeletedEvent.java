package com.cupidmeet.chatservice.websocket.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Событие об удалении чата.
 */
@Data
@AllArgsConstructor
public class ChatDeletedEvent {

    /**
     * Идентификатор удаленного чата.
     */
    private UUID chatId;
}