package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;

import java.util.List;

public interface FeedbackService {
    /**
     * Creates a user's complaint and sets its status to "NEW" by default.
     *
     * @param entity The complaint entity mapped in FeedbackController from dto.
     * @return The original complaint entity with status "NEW".
     * @throws ru.polskiy.feedbackservice.exception.CreateComplaintException
     */
    Complaint createComplaint(Complaint entity);

    /**
     * Creates a user's feedback.
     *
     * @param entity The feedback entity mapped from dto in FeedbackController.
     * @return The original feedback entity.
     * @throws ru.polskiy.feedbackservice.exception.CreateFeedbackException
     */
    Feedback createFeedback(Feedback entity);

    /**
     * Retrieves all complaints.
     *
     * @return A list of all complaint entities.
     */
    List<Complaint> findAllComplaints();

    /**
     * Retrieves all feedbacks.
     *
     * @return A list of all feedback entities.
     */
    List<Feedback> findAllFeedbacks();
}
