package ru.polskiy.feedbackservice.dto;

import ru.polskiy.feedbackservice.model.type.ComplaintType;

/**
 * DTO representing the request data needed from the user to create a complaint.
 *
 * @param toUserId The ID of the user who is the subject of the complaint.
 * @param comment  The text of the complaint.
 */
public record ComplaintRequest(
        Long toUserId,
        String comment,
        Long userId,
        ComplaintType type) {
}
