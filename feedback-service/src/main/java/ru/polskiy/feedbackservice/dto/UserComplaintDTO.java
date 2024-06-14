package ru.polskiy.feedbackservice.dto;

import ru.polskiy.feedbackservice.model.type.ComplaintType;
import ru.polskiy.feedbackservice.model.type.Status;

public record UserComplaintDTO(Long userId,
                               String comment,
                               Status status,
                               ComplaintType complaintType) {
}
