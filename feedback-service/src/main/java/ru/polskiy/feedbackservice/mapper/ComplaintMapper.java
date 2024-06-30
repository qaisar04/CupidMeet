package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.ComplaintCreateRequest;
import ru.polskiy.feedbackservice.dto.ComplaintResponse;
import ru.polskiy.feedbackservice.model.entity.Complaint;

/**
 * Mapper interface for converting between Complaint entities and ComplaintCreateDTOs.
 */
@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    /**
     * Converts ComplaintCreateDTO to Complaint entity.
     * Ignores the "id" field during conversion and sets default values for "status" and "complaintType".
     *
     * @param dto ComplaintCreateDTO to convert.
     * @return Complaint entity.
     */
    @Mapping(target = "status",  defaultValue = "NEW")
    @Mapping(target = "complaintType", source = "complaintType")
    Complaint toEntity(ComplaintCreateRequest dto);

    /**
     * Converts Complaint entity to ComplaintResponse.
     *
     * @param complaint The Complaint entity to convert.
     * @return ComplaintResponse representing the converted Complaint.
     */
    ComplaintResponse toResponse(Complaint complaint);
}
