package ru.polskiy.feedbackservice.dto;

/**
 * DTO used for creating a new Feedback entity.
 *
 * @param userId  The ID of the user providing the feedback.
 * @param comment The text of the feedback. This field cannot be empty and must be less than 500 characters.
 */
public record FeedbackCreateDTO(Long userId,
                                String comment) {
}
