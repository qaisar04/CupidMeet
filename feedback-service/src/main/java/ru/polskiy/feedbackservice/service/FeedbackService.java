package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    /**
     * Creates a user's feedback.
     *
     * @param entity The feedback entity mapped from dto in FeedbackController.
     * @throws  ru.polskiy.feedbackservice.exception.CreateFeedbackException with bad request status
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
     * @return patched entity
     */
    Feedback patchFeedback(Feedback feedback);
}
