package com.cupidmeet.chatservice.domain.dto;

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
    private String preferredGender;

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