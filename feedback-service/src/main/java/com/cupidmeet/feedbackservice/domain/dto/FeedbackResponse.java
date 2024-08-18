package com.cupidmeet.feedbackservice.domain.dto;

import com.cupidmeet.feedbackservice.domain.type.Grade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * DTO, представляющий ответ с информацией о предоставленном отзыве.
 */
@Data
@Schema(description = "Ответ с информацией о предоставленном отзыве")
public class FeedbackResponse {

    @Schema(description = "Идентификатор отзыва")
    private UUID id;

    /**
     * Идентификатор пользователя, оставившего отзыв.
     */
    @Schema(description = "Идентификатор пользователя, оставившего отзыв")
    private UUID userId;

    /**
     * Оценка, выставленная пользователем, значение от 1 до 5.
     */
    @Schema(description = "Оценка, выставленная пользователем")
    private Grade grade;

    /**
     * Текст отзыва, оставленного пользователем.
     */
    @Schema(description = "Текст отзыва")
    private String comment;
}