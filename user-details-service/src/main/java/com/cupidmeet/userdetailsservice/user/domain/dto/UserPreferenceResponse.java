package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.PreferredGender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Ответ с предпочтениями пользователя.
 */
@Data
@Schema(description = "Ответ с предпочтениями пользователя")
public class UserPreferenceResponse {

    /**
     * Предпочтительный пол пользователя.
     */
    @Schema(description = "Предпочтительный пол пользователя")
    private PreferredGender preferredGender;

    /**
     * Минимальный предпочитаемый возраст.
     */
    @Schema(description = "Минимальный предпочитаемый возраст")
    private Integer minAge;

    /**
     * Максимальный предпочитаемый возраст.
     */
    @Schema(description = "Максимальный предпочитаемый возраст")
    private Integer maxAge;
}