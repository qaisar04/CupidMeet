package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.ComplaintReviewDto;
import ru.polskiy.feedbackservice.model.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ComplaintReviewDto toDto(Review complain);
    @Mapping(target = "id",ignore = true)
    Review toEntity(ComplaintReviewDto dto);
}
