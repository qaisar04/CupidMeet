package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.model.entity.Complaint;

import java.util.List;

public interface ComplaintService {

    /**
     * Creates a user's complaint and sets its status to "NEW" by default.
     *
     * @param entity The complaint entity mapped in FeedbackController from dto.
     * @return The original complaint entity with status "NEW".
     * @throws ru.polskiy.feedbackservice.exception.CreateComplaintException with bad request status
     */
    Complaint createComplaint(Complaint entity);

    /**
     * Retrieves all complaints.
     *
     * @return A list of all complaint entities.
     */
    List<Complaint> findAllComplaints();
}
