package kz.baltabayev.userdetailsservice.model.dto;

public record UserInfoResponse(
        String name,
        Integer age,
        String city,
        String personalityType,
        String bio
) {
}