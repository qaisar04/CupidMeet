package com.cupidmeet.userdetailsservice.user.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Запрос на создание пользователя.
 */
@Data
@Schema(description = "Информация о пользователе")
public class UserCreateRequest {

    /**
     * Уникальный идентификатор пользователя в Telegram.
     */
    @Schema(description = "Уникальный идентификатор пользователя в Telegram")
    private Long userTelegramId;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String username;

    /**
     * Предпочтительный пол пользователя.
     */
    @Schema(description = "Информация о предпочтениях пользователя")
    private UserPreferenceRequest userPreference;

    /**
     * Подробная информация о пользователе.
     */
    @Schema(description = "Подробная информация о пользователе")
    private UserInfoRequest userInfo;
}