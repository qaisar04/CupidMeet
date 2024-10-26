package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.PersonalityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Ответ с информацией о подходящем пользователе.
 */
@Data
@AllArgsConstructor
@Schema(description = "Ответ с информацией о подходящем пользователе")
public class UserMatchResponse {

    /**
     * Идентификатор пользователя.
     */
    @Schema(description = "ID пользователя")
    private UUID userId;

    /**
     * Имя пользователя.
     */
    @Schema(description = "Имя пользователя")
    private String name;

    /**
     * Город проживания пользователя.
     */
    @Schema(description = "Город проживания пользователя")
    private String city;

    /**
     * Возраст пользователя.
     */
    @Schema(description = "Возраст пользователя")
    private Integer age;

    /**
     * Тип личности пользователя.
     */
    @Schema(description = "Тип личности пользователя")
    private PersonalityType personalityType;

    /**
     * Краткая биография пользователя.
     */
    @Schema(description = "Краткая биография пользователя")
    private String bio;
}