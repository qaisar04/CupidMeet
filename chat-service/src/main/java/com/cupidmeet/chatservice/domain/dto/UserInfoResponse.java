package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Ответ с информацией о пользователе.
 */
@Data
@Schema(description = "Ответ с информацией о пользователе")
public class UserInfoResponse {

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String name;

    /**
     * Возраст пользователя.
     */
    @Schema(description = "Возраст пользователя")
    private Integer age;

    /**
     * Город проживания пользователя.
     */
    @Schema(description = "Город проживания пользователя")
    private String city;

    /**
     * Пол пользователя.
     */
    @Schema(description = "Пол пользователя")
    private String gender;

    /**
     * Тип личности пользователя.
     */
    @Schema(description = "Тип личности пользователя")
    private String personalityType;

    /**
     * Краткая биография пользователя.
     */
    @Schema(description = "Краткая биография пользователя")
    private String bio;
}