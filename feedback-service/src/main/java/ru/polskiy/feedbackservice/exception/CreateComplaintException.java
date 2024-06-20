package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when an error occurs in the service layer while creating a complaint.
 */
public class CreateComplaintException extends RuntimeException {

    public CreateComplaintException(String message) {
        super("Can't create complaint: " + message);
    }
}
