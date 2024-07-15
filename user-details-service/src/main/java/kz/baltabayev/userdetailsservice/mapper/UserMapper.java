package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Mapper interface for converting User entities to UserResponse DTOs.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserInfoMapper.class, UserPreferenceMapper.class}
)
public interface UserMapper {

    @Mapping(source = "userCreateRequest.id", target = "id")
    @Mapping(source = "userCreateRequest.username", target = "username")
    @Mapping(source = "userCreateRequest.preferredGender", target = "userPreference.preferredGender")
    @Mapping(source = "userCreateRequest.userInfoRequest", target = "userInfo")
    User toEntity(UserCreateRequest userCreateRequest);

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user The User entity object containing data to map.
     * @return The mapped UserResponse DTO.
     */
    @Mapping(source = "userPreference", target = "userPreference")
    @Mapping(source = "userInfo", target = "userInfo")
    UserResponse toResponse(User user);
}