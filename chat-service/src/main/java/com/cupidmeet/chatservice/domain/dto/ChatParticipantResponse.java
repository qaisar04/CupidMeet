package com.cupidmeet.chatservice.domain.dto;

import com.cupidmeet.chatservice.domain.enumeration.ParticipantRole;
import com.cupidmeet.chatservice.domain.enumeration.ParticipantStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
public class ChatParticipantResponse {

    @Schema(description = "Уникальный идентификатор участника")
    private UUID userId;

    @Schema(description = "Роль участника чата")
    private ParticipantRole role;

    @Schema(description = "Статус участника")
    private ParticipantStatus status;

    @Schema(description = "Дата присоединения к чату")
    private ZonedDateTime joinedAt;
}
