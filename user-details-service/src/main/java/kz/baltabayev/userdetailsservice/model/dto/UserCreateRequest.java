package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.Status;

public record UserCreateRequest(
        Long id,
        String username,
        UserInfoRequest userInfoRequest,
        UserPreferenceRequest  userPreferenceRequest
) {
}