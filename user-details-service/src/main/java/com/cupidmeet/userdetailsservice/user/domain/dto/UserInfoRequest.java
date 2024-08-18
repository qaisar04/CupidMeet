package com.cupidmeet.userdetailsservice.user.domain.dto;

import com.cupidmeet.userdetailsservice.user.domain.types.Gender;
import com.cupidmeet.userdetailsservice.user.domain.types.PersonalityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * Подробная информация о пользователе.
 */
@Data
@Schema(description = "Подробная информация о пользователе")
public class UserInfoRequest {

    /**
     * Имя пользователя.
     */
    @NotNull
    @Schema(description = "Имя пользователя")
    private String name;

    /**
     * Возраст пользователя.
     */
    @NotNull(message = "Возраст пользователя не может быть пустым")
    @Schema(description = "Возраст пользователя")
    private Integer age;

    /**
     * Город проживания пользователя.
     */
    @NotBlank(message = "Город проживания пользователя не может быть пустым")
    @Schema(description = "Город проживания пользователя")
    private String city;

    /**
     * Пол пользователя.
     */
    @NotBlank(message = "Пол пользователя не может быть пустым")
    @Schema(description = "Пол пользователя")
    private Gender gender;

    /**
     * Тип личности пользователя.
     */
    @NotBlank(message = "Тип личности пользователя не может быть пустым")
    @Schema(description = "Тип личности пользователя")
    private PersonalityType personalityType;

    /**
     * Краткая биография пользователя.
     */
    @NotNull(message = "Тип личности пользователя не может быть пустым")
    @Schema(description = "Краткая биография пользователя")
    private String bio;

    /**
     * Вложенные файлы пользователя.
     */
    @NotNull(message = "Вложенные файлы пользователя не могут быть пустым")
    @Schema(description = "Вложенные файлы пользователя")
    private Set<String> fileIds;
}