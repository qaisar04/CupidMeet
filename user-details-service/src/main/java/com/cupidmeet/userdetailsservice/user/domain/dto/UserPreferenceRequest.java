package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.PreferredGender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Информация о предпочтениях пользователе")
public class UserPreferenceRequest {

    @NotNull(message = "Предпочитаемый пол пользователя не может быть пустым")
    @Schema(description = "Предпочитаемый пол пользователя")
    private PreferredGender preferredGender;

    @NotNull(message = "Минимальный предпочитаемый возвраст не может быть пустым")
    @Min(value = 14, message = "Минимальный предпочитаемый возвраст должен быть больше 14")
    @Schema(description = "Минимальный предпочитаемый возвраст")
    private Integer minAge;

    @Max(value = 80, message = "Максимальный предпочитаемый возвраст должен быть меньше 80")
    @NotNull(message = "Максимальный предпочитаемый возвраст не может быть пустым")
    @Schema(description = "Максимальный предпочитаемый возвраст")
    private Integer maxAge;
}
