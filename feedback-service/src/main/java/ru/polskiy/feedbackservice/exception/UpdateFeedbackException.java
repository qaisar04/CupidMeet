package ru.polskiy.feedbackservice.exception;

import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * This exception is thrown when an error occurs in the service layer while creating feedback.
 */
public class UpdateFeedbackException extends RuntimeException {

    /**
     * Constructor for CreateFeedbackException.
     *
     * @param message error message, {@link Feedback} expected.
     */
    public UpdateFeedbackException(String message) {
        super("Failed to update feedback: %s".formatted(message));
    }
}
