package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateMessageRequest {

    @Schema(description = "Идентификатор чата")
    private UUID chatId;

    @Schema(description = "Идентификатор пользователя отправителя")
    private UUID userId;

    @Schema(description = "Текст сообщения")
    private String content;
}
