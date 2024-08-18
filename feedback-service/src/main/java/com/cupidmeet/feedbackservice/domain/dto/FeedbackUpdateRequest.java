package com.cupidmeet.feedbackservice.domain.dto;

import com.cupidmeet.feedbackservice.domain.type.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO для обновления существующего отзыва.
 */
@Data
public class FeedbackUpdateRequest {

    /**
     * Текст отзыва.
     */
    @Schema(description = "Текст отзыва")
    @Size(max = 500, message = "Комментарий должен содержать не более 500 символов")
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String comment;

    /**
     * Оценка.
     */
    @Schema(description = "Оценка")
    @NotNull(message = "Grade не должен быть null")
    private Grade grade;
}