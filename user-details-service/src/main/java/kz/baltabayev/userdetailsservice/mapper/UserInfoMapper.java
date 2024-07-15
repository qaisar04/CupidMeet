package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoResponse;
import kz.baltabayev.userdetailsservice.model.entity.UserInfo;
import kz.baltabayev.userdetailsservice.model.types.PersonalityType;
import org.mapstruct.*;

/**
 * Mapper interface for converting between UserInfoRequest, UserInfoResponse, and UserInfo entities.
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserInfoMapper {

    /**
     * Maps from UserInfoRequest to UserInfo entity.
     *
     * @param request The UserInfoRequest object containing data to map.
     * @return The mapped UserInfo entity.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "personalityType", target = "personalityType", qualifiedByName = "stringToPersonalityType")
    UserInfo toEntity(UserInfoRequest request);

    /**
     * Maps from UserInfo entity to UserInfoResponse DTO.
     *
     * @param userInfo The UserInfo entity object containing data to map.
     * @return The mapped UserInfoResponse DTO.
     */
    UserInfoResponse toResponse(UserInfo userInfo);

    /**
     * Converts a String representation of a personality type to uppercase.
     *
     * This method takes a String representing a personality type and converts it to uppercase.
     *
     * @param personalityType The String representation of the personality type to convert.
     * @return The input personality type string converted to uppercase.
     */
    @Named("stringToPersonalityType")
    default String stringToPersonalityType(String personalityType) {
        return personalityType.toUpperCase();
    }
}