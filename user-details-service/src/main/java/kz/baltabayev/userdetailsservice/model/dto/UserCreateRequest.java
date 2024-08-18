package kz.baltabayev.userdetailsservice.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UserCreateRequest(

        @Min(1)
        Long id,

        @NotNull(message = "Username cannot be null")
        @NotBlank
        @Size(min = 2, max = 100, message = "Username must be between 1 and 100 characters")
        String username,

        @NotNull(message = "Gender cannot be null")
        @Pattern(regexp = "MALE|FEMALE|ANY", message = "Preferred gender must be MALE, FEMALE or ANY")
        String preferredGender,

        @Valid
        UserInfoRequest userInfoRequest
) {
}