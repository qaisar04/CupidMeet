package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when an error occurs in the service layer while creating feedback.
 */
public class CreateFeedbackException extends RuntimeException {

    public CreateFeedbackException(String message) {
        super("Can't create feedback: " + message);
    }
}
