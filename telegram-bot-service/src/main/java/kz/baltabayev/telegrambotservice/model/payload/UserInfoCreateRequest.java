package kz.baltabayev.telegrambotservice.model.payload;

public record UserInfoCreateRequest(
        String name,
        Integer age,
        String city,
        String gender,
        String personalityType,
        String bio
) {
}