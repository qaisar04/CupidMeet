package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.UserComplaintDTO;
import ru.polskiy.feedbackservice.model.entity.Complaint;

@Mapper(componentModel = "spring")
public interface ComplaintMapper {
    UserComplaintDTO toDto(Complaint complaint);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", defaultValue = "NEW")
    @Mapping(target = "complaintType", source = "complaintType", defaultValue = "UNKNOWN")
    Complaint toEntity(UserComplaintDTO dto);
}
