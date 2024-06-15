package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.Status;

public record UserResponse
        (
                Long id,
                String username,
                Status status,
                UserPreferenceResponse userPreference,
                UserInfoResponse userInfo
        ) {
}