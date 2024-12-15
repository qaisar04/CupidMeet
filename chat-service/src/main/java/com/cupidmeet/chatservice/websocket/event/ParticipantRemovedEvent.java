package com.cupidmeet.chatservice.websocket.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Событие удаления участника из чата.
 */
@Data
@AllArgsConstructor
public class ParticipantRemovedEvent {

    /**
     * Идентификатор чата.
     */
    private UUID chatId;

    /**
     * Идентификатор удаленного участника.
     */
    private UUID userId;

    /**
     * Идентификатор инициатора удаления.
     */
    private UUID initiatorId;
}
