package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when a request is made to create a complaint against the same user who has already been complained about.
 */
public class ComplaintToThisUserAlreadyExistException extends RuntimeException {

    public ComplaintToThisUserAlreadyExistException(String message) {
        super("complaint against this user:" + message +
                "\nhas already been sent");
    }
}
