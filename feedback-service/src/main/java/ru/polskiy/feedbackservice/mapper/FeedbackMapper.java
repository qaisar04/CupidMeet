package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.FeedbackRequestDTO;
import ru.polskiy.feedbackservice.model.entity.Feedback;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    FeedbackRequestDTO toDto(Feedback complain);

    @Mapping(target = "id", ignore = true)
    Feedback toEntity(FeedbackRequestDTO dto);
}
