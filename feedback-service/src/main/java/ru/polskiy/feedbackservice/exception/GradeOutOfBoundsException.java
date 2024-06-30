package ru.polskiy.feedbackservice.exception;

/**
 * Exception thrown when a grade is out of the valid range.
 * This exception is used to indicate that a provided grade value is not within the expected range of 1 to 5.
 */
public class GradeOutOfBoundsException extends RuntimeException {

    /**
     * Constructs a new GradeOutOfBoundsException with the specified detail message.
     *
     * @param message The detail message, which is appended with the range information.
     */
    public GradeOutOfBoundsException(String message) {
        super(message + " is out of range between 1 and 5");
    }
}
