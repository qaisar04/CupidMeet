package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.entity.UserPreference;
import org.mapstruct.*;

import java.time.LocalDateTime;


/**
 * Mapper interface for converting User entities to UserResponse DTOs.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserInfoMapper.class, UserPreferenceMapper.class},
        imports = {LocalDateTime.class}
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
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
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

    /**
     * This method is executed as part of object mapping to perform additional operations
     * on objects after successfully mapping from {@code UserCreateRequest} to {@code User}.
     * It calculates the user's age, creates a new {@code UserPreference} object based on
     * adjusted age values, sets it as the user's preference, assigns the user ID from
     * {@code userCreateRequest}, and establishes bidirectional linkage with user information.
     *
     * @param userCreateRequest The source object being mapped from, of type {@link UserCreateRequest}.
     * @param user              The target object being mapped to, of type {@link User}, annotated with {@link org.mapstruct.MappingTarget}.
     */
    @AfterMapping
    default void afterMapping(UserCreateRequest userCreateRequest, @MappingTarget User user) {
        Integer age = user.getUserInfo().getAge();

        UserPreference userPreference = new UserPreference(user.getUserPreference().getPreferredGender(),
                age + 3, age - 3, user);

        user.setUserPreference(userPreference);
        user.setId(userCreateRequest.id());
        user.getUserInfo().setUser(user);
    }
}