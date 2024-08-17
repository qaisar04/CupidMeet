package com.cupidmeet.userdetailsservice.user.mapper;

import com.cupidmeet.userdetailsservice.user.domain.dto.UserCreateRequest;
import com.cupidmeet.userdetailsservice.user.domain.dto.UserResponse;
import com.cupidmeet.userdetailsservice.user.domain.entity.User;
import org.mapstruct.*;

import java.time.LocalDateTime;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserInfoMapper.class, UserPreferenceMapper.class},
        imports = {LocalDateTime.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "role", constant = "USER")
    User toEntity(UserCreateRequest userCreateRequest);

    UserResponse toResponse(User user);

    @AfterMapping
    default void afterMapping(@MappingTarget User user) {
        user.getUserInfo().setUser(user);
        user.getUserPreference().setUser(user);
    }
}