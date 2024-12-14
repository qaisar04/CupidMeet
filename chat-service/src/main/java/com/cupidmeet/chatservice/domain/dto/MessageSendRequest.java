package com.cupidmeet.chatservice.domain.dto;

import com.cupidmeet.chatservice.domain.enumeration.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MessageSendRequest {

    @Schema(description = "Идентификатор чата")
    private UUID chatId;

    @Schema(description = "Содержание сообщения")
    private String content;

    @Schema(description = "Тип сообщения")
    private MessageType type;
}
