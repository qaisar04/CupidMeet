package ru.polskiy.feedbackservice.dto;

/**
 * DTO representing the request data needed from the user to create a feedback.
 *
 * @param comment The text of the feedback.
 */
public record FeedbackRequestDTO(String comment) {
}
