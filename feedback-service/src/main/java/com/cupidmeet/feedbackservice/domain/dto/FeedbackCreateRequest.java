package com.cupidmeet.feedbackservice.domain.dto;

import com.cupidmeet.feedbackservice.domain.type.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 * DTO для создания нового отзыва.
 */
@Data
@Schema(description = "Запрос на создание отзыва")
public class FeedbackCreateRequest {

    /**
     * Идентификатор пользователя, оставляющего отзыв.
     */
    @Schema(description = "Идентификатор пользователя, оставляющего отзыв")
    @NotNull(message = "Идентификатор пользователя не должен быть null")
    private UUID userId;

    /**
     * Текст отзыва.
     */
    @Schema(description = "Текст отзыва")
    @Size(max = 500, message = "Комментарий должен содержать не более 500 символов")
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String comment;

    /**
     * Оценка, которую оставил пользователь.
     */
    @Schema(description = "Оценка, оставленная пользователем")
    @NotNull(message = "Оценка не должна быть null")
    private Grade grade;
}
