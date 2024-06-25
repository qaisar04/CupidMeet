package ru.polskiy.feedbackservice.dto;

import jakarta.validation.constraints.Size;

/**
 * DTO representing the request data needed from the user to create a feedback.
 *
 * @param comment The text of the feedback.
 */
public record FeedbackRequest(Long userId,
                              String comment,
                              Byte grade) {
}
