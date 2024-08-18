package com.cupidmeet.feedbackservice.domain.dto;

import com.cupidmeet.feedbackservice.domain.type.ComplaintType;
import com.cupidmeet.feedbackservice.domain.type.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * DTO для ответа с информацией о жалобе.
 */
@Data
@Schema(description = "Ответ с информацией о жалобе")
public class ComplaintResponse {

    /**
     * Идентификатор жалобы.
     */
    @Schema(description = "Идентификатор жалобы")
    private UUID id;

    /**
     * Идентификатор пользователя, который подал жалобу.
     */
    @Schema(description = "Идентификатор пользователя, который подал жалобу")
    private UUID fromUserId;

    /**
     * Идентификатор пользователя, на которого подана жалоба.
     */
    @Schema(description = "Идентификатор пользователя, на которого подана жалоба")
    private UUID toUserId;

    /**
     * Текст жалобы.
     */
    @Schema(description = "Текст жалобы")
    private String comment;

    /**
     * Статус жалобы.
     */
    @Schema(description = "Статус жалобы")
    private Status status;

    /**
     * Тип жалобы.
     */
    @Schema(description = "Тип жалобы")
    private ComplaintType complaintType;
}