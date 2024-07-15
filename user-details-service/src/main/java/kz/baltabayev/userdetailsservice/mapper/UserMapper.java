package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

/**
 * Mapper interface for converting User entities to UserResponse DTOs.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserInfoMapper.class, UserPreferenceMapper.class}
)
public interface UserMapper {

    /**
     * Converts a {@link UserCreateRequest} object to a {@link User} entity.
     *
     * @param userCreateRequest The {@link UserCreateRequest} object to be converted.
     * @return A {@link User} entity with fields mapped from {@link UserCreateRequest}.
     */
    @Mapping(source = "preferredGender", target = "userPreference.preferredGender", qualifiedByName = "stringToPreferredGender")
    @Mapping(source = "userInfoRequest", target = "userInfo")
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

    /**
     * Converts a String representation of a preferred gender type to uppercase.
     * <p>
     * This method takes a String representing a personality type and converts it to uppercase.
     *
     * @param preferredGender The String representation of the preferred gender to convert.
     * @return The input personality type string converted to uppercase.
     */
    @Named("stringToPreferredGender")
    default String stringToPreferredGender(String preferredGender) {
        return preferredGender.toUpperCase();
    }
}