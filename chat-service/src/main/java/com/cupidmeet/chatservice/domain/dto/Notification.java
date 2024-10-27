package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Notification {

    @Schema(description = "Идентификатор сообщения")
    private UUID id;

    @Schema(description = "Идентификатор отправителя")
    private UUID senderId;

    @Schema(description = "Идентификатор получателя")
    private UUID chatId;

    @Schema(description = "Содержимое уведомления")
    private String content;
}
