package com.cupidmeet.userdetailsservice.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;

/**
 * Запрос на создание пользователя.
 */
@Data
@Schema(description = "Информация о пользователе")
public class UserCreateRequest {

    /**
     * Предпочтительный пол пользователя.
     */
    @Valid
    @Schema(description = "Информация о предпочтениях пользователя")
    private UserPreferenceRequest userPreference;

    /**
     * Подробная информация о пользователе.
     */
    @Valid
    @Schema(description = "Подробная информация о пользователе")
    private UserInfoRequest userInfo;
}