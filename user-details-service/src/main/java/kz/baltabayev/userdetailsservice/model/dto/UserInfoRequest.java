package kz.baltabayev.userdetailsservice.model.dto;

import jakarta.validation.constraints.*;

import java.util.Locale;
import java.util.Set;

public record UserInfoRequest(
        @NotNull(message = "Name cannot be null")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @NotNull(message = "Age cannot be null")
        @Min(value = 0, message = "Age must be greater than or equal to 0")
        @Max(value = 150, message = "Age must be less than or equal to 150")
        Integer age,

        @NotNull(message = "City cannot be null")
        @Size(min = 1, max = 100, message = "City must be between 1 and 100 characters")
        String city,

        @NotNull(message = "Gender cannot be null")
        @Pattern(regexp = "MALE|FEMALE", message = "Gender must be either MALE or FEMALE")
        String gender,

        @NotNull(message = "PersonalityType cannot be null")
        @Pattern(regexp = "INTJ|INTP|ENTJ|ENTP|INFJ|INFP|ENFJ|ENFP|ISTJ|ISFJ|ESTJ|ESFJ|ISTP|ISFP|ESTP|",
                message = "PersonalityType must be a valid personality type")
        String personalityType,

        @Size(max = 255, message = "Bio must be less than or equal to 255 characters")
        String bio,

        @NotNull
        Set<String> fileIds
) {
}