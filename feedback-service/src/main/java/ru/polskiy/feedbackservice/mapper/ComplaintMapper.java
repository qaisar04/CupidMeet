package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.ComplaintReviewDto;
import ru.polskiy.feedbackservice.model.entity.Complaint;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    ComplaintReviewDto toDto(Complaint complaint);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "status",ignore = true)
    Complaint toEntity(ComplaintReviewDto complaintReviewDto);
}
