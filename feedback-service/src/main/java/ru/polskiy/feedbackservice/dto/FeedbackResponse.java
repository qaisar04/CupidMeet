package ru.polskiy.feedbackservice.dto;

/**
 * Represents a response object for feedback.
 */
public record FeedbackResponse(
        String comment,
        Byte grade) {
}
