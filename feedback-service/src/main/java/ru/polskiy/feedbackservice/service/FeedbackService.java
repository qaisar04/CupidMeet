package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.exception.UpdateFeedbackException;
import ru.polskiy.feedbackservice.model.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    /**
     * Creates a user's feedback.
     *
     * @param entity The feedback entity mapped from dto in FeedbackController.
     * @throws UpdateFeedbackException with bad request status
     */
    void createFeedback(Feedback entity);

    /**
     * Retrieves all feedbacks.
     *
     * @return A list of all feedback entities.
     */
    List<Feedback> findAllFeedbacks();

    /**
     * Updating existed feedback
     *
     * @param feedback entity that patches.
     */
    void updateFeedback(Feedback feedback);
}
