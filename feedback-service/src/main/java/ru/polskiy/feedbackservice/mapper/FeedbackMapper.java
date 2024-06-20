package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.FeedbackCreateDTO;
import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * Mapper interface for converting between Feedback entities and FeedbackCreateDTOs.
 */
@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    /**
     * Converts Feedback entity to FeedbackCreateDTO.
     *
     * @param feedback Feedback entity to convert.
     * @return FeedbackCreateDTO representing the entity.
     */
    FeedbackCreateDTO toDto(Feedback feedback);

    /**
     * Converts FeedbackCreateDTO to Feedback entity.
     * Ignores the "id" field during conversion.
     *
     * @param dto FeedbackCreateDTO to convert.
     * @return Feedback entity.
     */
    @Mapping(target = "id", ignore = true)
    Feedback toEntity(FeedbackCreateDTO dto);
}
