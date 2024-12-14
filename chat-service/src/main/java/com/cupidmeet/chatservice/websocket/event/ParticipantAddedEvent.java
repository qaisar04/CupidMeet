package com.cupidmeet.chatservice.websocket.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Событие удаления участника из чата.
 */
@Data
@AllArgsConstructor
public class ParticipantAddedEvent {

    /**
     * Идентификатор чата.
     */
    private UUID chatId;

    /**
     * Идентификатор добавленного участника.
     */
    private UUID userId;

    /**
     * Идентификатор инициатора добавления.
     */
    private UUID initiatorId;
}
