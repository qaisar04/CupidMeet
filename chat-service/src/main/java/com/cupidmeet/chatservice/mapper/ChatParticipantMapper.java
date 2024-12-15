package com.cupidmeet.chatservice.mapper;

import com.cupidmeet.chatservice.domain.dto.ChatParticipantResponse;
import com.cupidmeet.chatservice.domain.entity.ChatParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatParticipantMapper {

    /**
     * Преобразование участника чата в ответ.
     *
     * @param chatParticipant участник чата
     * @return ответ с информацией об участнике чата
     */
    ChatParticipantResponse toResponse(ChatParticipant chatParticipant);
}
