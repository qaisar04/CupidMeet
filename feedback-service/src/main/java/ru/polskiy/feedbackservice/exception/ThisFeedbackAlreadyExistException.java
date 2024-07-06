package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when feedback from this user already exists.
 */
public class ThisFeedbackAlreadyExistException extends RuntimeException {

    public ThisFeedbackAlreadyExistException(String message) {
        super("The feedback with userId: %s already exists".formatted(message));
    }
}