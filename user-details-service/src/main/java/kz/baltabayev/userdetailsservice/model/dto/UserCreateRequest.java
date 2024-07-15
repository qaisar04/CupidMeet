package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.PreferredGender;
import kz.baltabayev.userdetailsservice.model.types.Status;

public record UserCreateRequest(
        Long id,
        String username,
        PreferredGender preferredGender,
        UserInfoRequest userInfoRequest
) {
}