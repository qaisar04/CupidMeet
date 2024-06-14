package ru.polskiy.feedbackservice.exception;


public class CreateComplaintException extends RuntimeException {

    public CreateComplaintException(String message) {
        super("Can't create complaint: " + message);
    }
}
