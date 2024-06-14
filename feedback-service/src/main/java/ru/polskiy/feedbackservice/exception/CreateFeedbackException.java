package ru.polskiy.feedbackservice.exception;

public class CreateFeedbackException extends RuntimeException {

    public CreateFeedbackException(String message) {
        super("Can't create feedback: " + message);
    }
}
