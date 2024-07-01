package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import ru.polskiy.feedbackservice.model.entity.Complaint;

import java.util.List;

public interface ComplaintService {

    /**
     * Creates a user's complaint and sets its status to "NEW" by default.
     *
     * @param entity The complaint entity mapped in FeedbackController from dto.
     * @throws ComplaintToThisUserAlreadyExistException with conflict status
     * @throws IllegalArgumentException  with bad request status
     */
    void createComplaint(Complaint entity);

    /**
     * Retrieves all complaints.
     *
     * @return A list of all complaint entities.
     */
    List<Complaint> findAllComplaints();
}
