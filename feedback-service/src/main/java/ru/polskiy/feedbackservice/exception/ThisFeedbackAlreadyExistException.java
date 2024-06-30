package ru.polskiy.feedbackservice.exception;

import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * This exception is thrown when feedback from this user already exists.
 */
public class ThisFeedbackAlreadyExistException extends RuntimeException {

    public ThisFeedbackAlreadyExistException(Feedback entity) {
        super("The feedback: " + entity + " already exists in database");
    }
}
