package com.cupidmeet.chatservice.domain.dto;

import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ChatResponse {

    @Schema(description = "Идентификатор чата")
    private UUID id;

    @Schema(description = "Название чата")
    private String name;

    @Schema(description = "Тип чата")
    private ChatType chatType;

    @Schema(description = "Дата создания чата")
    private ZonedDateTime createdAt;

    private Set<UUID> participants;
}
