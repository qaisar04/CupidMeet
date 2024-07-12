package kz.baltabayev.userdetailsservice.mapper;

import kz.baltabayev.userdetailsservice.model.dto.UserCreateRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserInfoRequest;
import kz.baltabayev.userdetailsservice.model.dto.UserResponse;
import kz.baltabayev.userdetailsservice.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserInfoMapper.class, UserPreferenceMapper.class})
public interface UserMapper {

    @Mapping(source = "userCreateRequest.id", target = "id")
    @Mapping(source = "userCreateRequest.username", target = "username")
    @Mapping(source = "gender", target = "userPreference.preferredGender")
    @Mapping(source = "userInfoRequest", target = "userInfo")
    User toEntity(UserCreateRequest userCreateRequest, String gender, UserInfoRequest userInfoRequest);

    @Mapping(source = "userPreference", target = "userPreference")
    @Mapping(source = "userInfo", target = "userInfo")
    UserResponse toResponse(User user);
}