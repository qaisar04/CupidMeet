package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserPreferenceResponse;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting UserPreference entities to UserPreferenceResponse DTOs.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface UserPreferenceMapper {

    /**
     * Converts a UserPreference entity to a UserPreferenceResponse DTO.
     *
     * @param userPreference The UserPreference entity object containing data to map.
     * @return The mapped UserPreferenceResponse DTO.
     */
    UserPreferenceResponse toUserPreferenceResponse(UserPreference userPreference);
}