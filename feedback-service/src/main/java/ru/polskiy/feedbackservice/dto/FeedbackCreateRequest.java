package ru.polskiy.feedbackservice.dto;

import jakarta.validation.constraints.*;

/**
 * DTO used for creating a new Feedback entity.
 *
 * @param userId  The ID of the user providing the feedback.
 * @param comment The text of the feedback. This field cannot be empty and must be less than 500 characters.
 */
public record FeedbackCreateRequest(

        Long userId,

        @Size(max = 500, message = "comment must be less than 500 or equal")
        @NotNull(message = "comment mustn't be null")
        @NotBlank(message = "comment mustn't be blank")
        String comment,

        @Min(value = 1, message = "grade must be in range from 1 to 5")
        @Max(value = 5, message = "grade must be in range from 1 to 5")
        Byte grade
) {
}
