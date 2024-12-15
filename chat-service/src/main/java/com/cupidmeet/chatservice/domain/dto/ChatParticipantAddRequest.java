package com.cupidmeet.chatservice.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChatParticipantAddRequest {

    private UUID userId;
}
