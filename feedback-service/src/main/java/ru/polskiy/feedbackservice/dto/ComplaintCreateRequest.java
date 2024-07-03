package ru.polskiy.feedbackservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public record ComplaintCreateRequest(

        Long fromUserId,

        Long toUserId,

        @Size(max = 500, message = "comment must be less than 500 or equal")
        @NotNull(message = "comment mustn't be null")
        @NotBlank(message = "comment mustn't be blank")
        String comment,

        Status status,
        @NotNull(message = "type mustn't be null")
        ComplaintType complaintType) {
}
