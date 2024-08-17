package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.Role;
import com.cupidmeet.userdetailsservice.user.domain.types.Status;
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
    private Status status;

    /**
     * Роль пользователя.
     */
    @Schema(description = "Роль пользователя.")
    private Role role;

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