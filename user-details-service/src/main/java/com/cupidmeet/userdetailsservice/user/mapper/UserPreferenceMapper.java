package com.cupidmeet.userdetailsservice.user.mapper;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserPreferenceResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.UserPreference;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting UserPreference entities to UserPreferenceResponse DTOs.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserPreferenceMapper {

    UserPreferenceResponse toUserPreferenceResponse(UserPreference userPreference);
}