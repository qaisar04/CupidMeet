package kz.baltabayev.userdetailsservice.model.dto;

import kz.baltabayev.userdetailsservice.model.types.Status;

public class UserResponse {
    private Long id;
    private String username;
    private Status status;
    private UserPreferenceResponse userPreference;
    private UserInfoResponse userInfo;
}