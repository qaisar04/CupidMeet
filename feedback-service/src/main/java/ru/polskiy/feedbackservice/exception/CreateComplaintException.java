package ru.polskiy.feedbackservice.exception;

import ru.polskiy.feedbackservice.model.entity.Complaint;
import ru.polskiy.feedbackservice.model.entity.Feedback;

/**
 * This exception is thrown when an error occurs in the service layer while creating a complaint.
 */
public class CreateComplaintException extends RuntimeException {

    /**
     * Constructor for CreateComplaintException.
     *
     * @param message error message, {@link Complaint} expected.
     */
    public CreateComplaintException(String message) {
        super("Failed to create complaint: " + message);
    }
    //TODO no need in this exception if others are adorable
}
