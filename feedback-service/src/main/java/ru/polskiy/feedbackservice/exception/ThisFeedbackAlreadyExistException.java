package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when feedback from this user already exists.
 */
public class ThisFeedbackAlreadyExistException extends RuntimeException {

    public ThisFeedbackAlreadyExistException(String message) {
        super("feedback: " + message + " already exist");
    }
}
