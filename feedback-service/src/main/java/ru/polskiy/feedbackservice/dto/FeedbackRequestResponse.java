package ru.polskiy.feedbackservice.dto;

import jakarta.validation.constraints.*;

/**
 * Represents a response object for feedback.
 */
public record FeedbackRequestResponse(
        @Size(max = 500, message = "comment must be less than 500 or equal")
        @NotNull(message = "comment mustn't be null")
        @NotBlank(message = "comment mustn't be blank")
        String comment,

        @Min(value = 1, message = "grade must be in range from 1 to 5")
        @Max(value = 5, message = "grade must be in range from 1 to 5")
        Byte grade) {
}
