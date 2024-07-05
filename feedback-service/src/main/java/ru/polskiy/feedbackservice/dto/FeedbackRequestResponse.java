package ru.polskiy.feedbackservice.dto;

import jakarta.validation.constraints.*;
import ru.polskiy.feedbackservice.model.type.Grade;

/**
 * Represents a response object for feedback.
 */
public record FeedbackRequestResponse(
        @Size(max = 500, message = "comment must be less than 500 or equal")
        @NotNull(message = "comment mustn't be null")
        @NotBlank(message = "comment mustn't be blank")
        String comment,

        Grade grade
) {
}
