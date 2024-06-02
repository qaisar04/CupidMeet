package kz.baltabayev.userdetailsservice.model.dto;

public record UserInfoCreateRequest(
        String name,
        Integer age,
        String city,
        String gender,
        String personalityType,
        String bio
) {
}