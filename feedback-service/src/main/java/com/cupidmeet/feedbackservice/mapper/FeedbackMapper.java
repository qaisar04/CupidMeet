package com.cupidmeet.feedbackservice.mapper;

import com.cupidmeet.feedbackservice.domain.dto.FeedbackResponse;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackUpdateRequest;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cupidmeet.feedbackservice.domain.dto.FeedbackCreateRequest;
import com.cupidmeet.feedbackservice.domain.entity.Feedback;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    Feedback toEntity(FeedbackCreateRequest request);

    FeedbackResponse toResponse(Feedback feedback);

    void updateEntityFromDto(FeedbackUpdateRequest request, @MappingTarget Feedback feedback);
}