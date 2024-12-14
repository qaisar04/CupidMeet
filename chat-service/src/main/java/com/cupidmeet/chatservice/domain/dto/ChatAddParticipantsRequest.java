package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ChatAddParticipantsRequest {

    @Schema(description = "Список идентификаторов участников")
    private Set<UUID> userIds;
}
