package ru.polskiy.feedbackservice.dto;

/**
 * Represents a response object for complaints.
 */
public record ComplaintResponse(
        Long toUserId,
        String comment) {
}
