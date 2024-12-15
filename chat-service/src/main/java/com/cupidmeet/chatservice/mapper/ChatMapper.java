package com.cupidmeet.chatservice.mapper;

import com.cupidmeet.chatservice.domain.dto.ChatCreateRequest;
import com.cupidmeet.chatservice.domain.dto.ChatResponse;
import com.cupidmeet.chatservice.domain.entity.Chat;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatMapper {

    /**
     * Преобразование запроса на создание чата в сущность чата.
     *
     * @param request запрос на создание чата
     * @return сущность чата
     */
    @Mapping(target = "participants", source = "participants", qualifiedByName = "mapParticipantIds")
    Chat toEntity(ChatCreateRequest request);

    /**
     * Преобразование сущности чата в ответ.
     *
     * @param chat сущность чата
     * @return ответ с информацией о чате
     */
    @Mapping(target = "participants", source = "participants", qualifiedByName = "mapToIds")
    ChatResponse toResponse(Chat chat);

    /**
     * Преобразование идентификаторов участников чата в сущности участников.
     *
     * @param participantIds идентификаторы участников
     * @return сущности участников
     */
    @Named("mapParticipantIds")
    default Set<ChatParticipant> map(Set<UUID> participantIds) {
        return participantIds.stream()
                .map(id -> ChatParticipant.builder()
                        .userId(id)
                        .build())
                .collect(Collectors.toSet());
    }

    /**
     * Преобразование участников чата в их идентификаторы.
     *
     * @param participants сущности участников
     * @return идентификаторы участников
     */
    @Named("mapToIds")
    default Set<UUID> mapToIds(Set<ChatParticipant> participants) {
        return participants.stream()
                .map(ChatParticipant::getUserId)
                .collect(Collectors.toSet());
    }
}
