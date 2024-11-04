package com.cupidmeet.chatservice.domain.dto;

import com.cupidmeet.chatservice.domain.enumeration.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CreateChatRequest {

    @Schema(description = "Тип чата")
    private ChatType chatType;

    @Schema(description = "Название чата")
    private String name;

    @Schema(description = "Список участников чата")
    private Set<UUID> participants;
}
