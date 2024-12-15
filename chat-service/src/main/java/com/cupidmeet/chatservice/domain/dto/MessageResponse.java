package com.cupidmeet.chatservice.domain.dto;

import com.cupidmeet.chatservice.domain.enumeration.MessageStatus;
import com.cupidmeet.chatservice.domain.enumeration.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class MessageResponse {

    @Schema(description = "Идентификатор сообщения")
    private UUID id;

    @Schema(description = "Идентификатор чата")
    private UUID chatId;

    @Schema(description = "Cодержание сообщения")
    private String content;

    @Schema(description = "Тип сообщения")
    private MessageType type;

    @Schema(description = "Статус сообщения")
    private MessageStatus status;

    @Schema(description = "Идентификатор отправителя")
    private ZonedDateTime timestamp;
}
