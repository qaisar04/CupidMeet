package ru.polskiy.feedbackservice.dto;

import ru.polskiy.feedbackservice.model.type.ComplaintType;
import ru.polskiy.feedbackservice.model.type.Status;

/**
 * DTO used for creating a new Complaint.
 *
 * @param fromUserId    The ID of the user who filed the complaint.
 * @param toUserId      The ID of the user who is the subject of the complaint.
 * @param comment       The text of the complaint.
 * @param status        The initial status of the complaint.
 * @param complaintType The type of complaint being filed.
 */
public record ComplaintCreateDTO(Long fromUserId,
                                 Long toUserId,
                                 String comment,
                                 Status status,
                                 ComplaintType complaintType) {
}
