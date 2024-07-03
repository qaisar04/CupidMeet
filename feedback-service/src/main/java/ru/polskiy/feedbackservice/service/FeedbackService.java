package ru.polskiy.feedbackservice.service;

import ru.polskiy.feedbackservice.dto.FeedbackRequestResponse;
import ru.polskiy.feedbackservice.exception.ThisFeedbackAlreadyExistException;
import ru.polskiy.feedbackservice.model.entity.Feedback;

import java.util.List;

public interface FeedbackService {

    /**
     * Creates a user's feedback.
     *
     * @param entity The feedback entity mapped from dto in FeedbackController.
     * @throws ThisFeedbackAlreadyExistException with status conflict
     */
    void createFeedback(Feedback entity);

    /**
     * Retrieves all feedbacks.
     *
     * @return A list of all feedback entities.
     */
    List<Feedback> findAllFeedbacks();

    /**
     * Updates the feedback for a given user.
     *
     * @param userId      the ID of the user whose feedback is being updated; must not be null
     * @param feedbackDto data provided by the user, for updating comment and grade
     * @throws IllegalArgumentException if userId is null
     * @throws IllegalArgumentException if grade is null or not between 1 and 5
     */
    void updateFeedback(Long userId, FeedbackRequestResponse feedbackDto);
}
