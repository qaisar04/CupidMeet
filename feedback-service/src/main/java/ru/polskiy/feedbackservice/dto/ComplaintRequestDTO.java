package ru.polskiy.feedbackservice.dto;

/**
 * DTO representing the request data needed from the user to create a complaint.
 *
 * @param toUserId The ID of the user who is the subject of the complaint.
 * @param comment  The text of the complaint.
 */
public record ComplaintRequestDTO(
        Long toUserId,
        String comment) {
}
