package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * Ответ с информацией о пользователе.
 */
@Data
@Schema(description = "Ответ с информацией о пользователе")
public class UserResponse {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Schema(description = "Уникальный идентификатор пользователя")
    private UUID id;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String username;

    /**
     * Статус пользователя.
     */
    @Schema(description = "Статус пользователя")
    private String status;

    /**
     * Роль пользователя.
     */
    @Schema(description = "Роль пользователя.")
    private String role;

    /**
     * Предпочтения пользователя.
     */
    @Schema(description = "Предпочтения пользователя")
    private UserPreferenceResponse userPreference;

    /**
     * Подробная информация о пользователе.
     */
    @Schema(description = "Подробная информация о пользователе")
    private UserInfoResponse userInfo;
}