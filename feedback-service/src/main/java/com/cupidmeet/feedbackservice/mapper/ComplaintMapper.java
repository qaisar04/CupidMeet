package com.cupidmeet.feedbackservice.mapper;

import com.cupidmeet.feedbackservice.domain.dto.ComplaintResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.cupidmeet.feedbackservice.domain.dto.ComplaintCreateRequest;
import com.cupidmeet.feedbackservice.domain.entity.Complaint;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    @Mapping(target = "status", defaultValue = "NEW")
    @Mapping(target = "complaintType", source = "complaintType")
    Complaint toEntity(ComplaintCreateRequest request);

    ComplaintResponse toResponse(Complaint complaint);
}