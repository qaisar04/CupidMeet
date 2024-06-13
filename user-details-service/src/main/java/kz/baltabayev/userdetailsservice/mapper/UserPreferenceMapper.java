package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserPreferenceResponse;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserPreferenceMapper {

    UserPreferenceResponse toUserPreferenceResponse(UserPreference userPreference);
}