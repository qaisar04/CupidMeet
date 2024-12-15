package com.cupidmeet.chatservice.websocket.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Событие об удалении сообщения.
 */
@Data
@AllArgsConstructor
public class MessageDeletedEvent {

    /**
     * Идентификатор удаленного сообщения.
     */
    private UUID messageId;
}
