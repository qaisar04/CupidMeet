package ru.polskiy.feedbackservice.exception;

/**
 * Exception thrown when a feedback object is not found in the database.
 * This exception is used to indicate that the specified feedback object does not exist and cannot be patched.
 */
public class NoSuchFeedbackException extends RuntimeException {

    /**
     * Constructs a new NoSuchFeedbackException with the specified detail message.
     *
     * @param message The detail message, which is appended with the information about non-existence in the database.
     */
    public NoSuchFeedbackException(String message) {
        super("This object:" + message + " doesn't exist in database and can't be patched");
    }
}
