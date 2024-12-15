package com.cupidmeet.chatservice.mapper;

import com.cupidmeet.chatservice.domain.dto.MessageResponse;
import com.cupidmeet.chatservice.domain.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MessageMapper {

    /**
     * Преобразовать сообщение в ответ.
     *
     * @param message сообщение
     * @return ответ
     */
    MessageResponse toResponse(Message message);
}
