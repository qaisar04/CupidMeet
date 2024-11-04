package com.cupidmeet.chatservice.mapper;

import com.cupidmeet.chatservice.domain.dto.CreateChatRequest;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    @Mapping(target = "participants", source = "participants")
    Chat toEntity(CreateChatRequest request);

    default Set<ChatParticipant> map(Set<UUID> participantIds) {
        return participantIds.stream()
                .map(id -> ChatParticipant.builder()
                        .userId(id)
                        .build())
                .collect(Collectors.toSet());
    }
}
