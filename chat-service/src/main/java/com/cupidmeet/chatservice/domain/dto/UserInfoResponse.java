package com.cupidmeet.chatservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ с информацией о пользователе")
public class UserInfoResponse {

    @Schema(description = "Имя пользователя")
    private String name;

    @Schema(description = "Возраст пользователя")
    private Integer age;

    @Schema(description = "Город проживания пользователя")
    private String city;

    @Schema(description = "Пол пользователя")
    private String gender;

    @Schema(description = "Тип личности пользователя")
    private String personalityType;

    @Schema(description = "Краткая биография пользователя")
    private String bio;
}