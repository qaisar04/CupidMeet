package com.cupidmeet.feedbackservice.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.cupidmeet.feedbackservice.domain.type.ComplaintType;
import com.cupidmeet.feedbackservice.domain.type.Status;
import lombok.Data;

import java.util.UUID;

/**
 * DTO для создания новой жалобы.
 */
@Data
@Schema(description = "Запрос на создание жалобы")
public class ComplaintCreateRequest {

    /**
     * Идентификатор пользователя, который подал жалобу.
     */
    @Schema(description = "Идентификатор пользователя, который подал жалобу")
    @NotNull(message = "Идентификатор пользователя, подавшего жалобу, не должен быть null")
    private UUID fromUserId;

    /**
     * Идентификатор пользователя, на которого подана жалоба.
     */
    @Schema(description = "Идентификатор пользователя, на которого подана жалоба")
    @NotNull(message = "Идентификатор пользователя, на которого подана жалоба, не должен быть null")
    private UUID toUserId;

    /**
     * Текст жалобы.
     */
    @Schema(description = "Текст жалобы")
    @Size(max = 500, message = "Комментарий должен содержать не более 500 символов")
    @NotBlank(message = "Комментарий не должен быть пустым")
    private String comment;

    /**
     * Начальный статус жалобы.
     */
    @Schema(description = "Начальный статус жалобы")
    private Status status;

    /**
     * Тип подаваемой жалобы.
     */
    @Schema(description = "Тип подаваемой жалобы")
    @NotNull(message = "Тип жалобы не должен быть null")
    private ComplaintType complaintType;
}