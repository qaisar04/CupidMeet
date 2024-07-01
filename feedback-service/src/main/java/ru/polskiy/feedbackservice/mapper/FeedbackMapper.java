package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import ru.polskiy.feedbackservice.dto.FeedbackCreateRequest;
//import ru.polskiy.feedbackservice.dto.FeedbackRequest;
import ru.polskiy.feedbackservice.dto.FeedbackRequestResponse;
import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * Mapper interface for converting between Feedback entities and FeedbackCreateDTOs.
 */
@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    /**
     * Converts FeedbackCreateDTO to Feedback entity.
     * Ignores the "id" field during conversion.
     *
     * @param dto FeedbackCreateDTO to convert.
     * @return Feedback entity.
     */
    Feedback toEntity(FeedbackCreateRequest dto);

    FeedbackRequestResponse toResponse(Feedback feedback);
}
