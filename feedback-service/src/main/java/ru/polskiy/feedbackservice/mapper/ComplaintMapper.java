package ru.polskiy.feedbackservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.feedbackservice.dto.ComplaintCreateDTO;
import ru.polskiy.feedbackservice.model.entity.Complaint;

/**
 * Mapper interface for converting between Complaint entities and ComplaintCreateDTOs.
 */
@Mapper(componentModel = "spring")
public interface ComplaintMapper {

    /**
     * Converts Complaint entity to ComplaintCreateDTO.
     *
     * @param complaint Complaint entity to convert.
     * @return ComplaintCreateDTO representing the entity.
     */
    ComplaintCreateDTO toDto(Complaint complaint);

    /**
     * Converts ComplaintCreateDTO to Complaint entity.
     * Ignores the "id" field during conversion and sets default values for "status" and "complaintType".
     *
     * @param dto ComplaintCreateDTO to convert.
     * @return Complaint entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status", defaultValue = "NEW")
    @Mapping(target = "complaintType", source = "complaintType", defaultValue = "UNKNOWN")
    Complaint toEntity(ComplaintCreateDTO dto);
}
