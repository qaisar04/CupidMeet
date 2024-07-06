package ru.polskiy.feedbackservice.exception;

/**
 * This exception is thrown when a request is made to create a complaint against the same user who has already been complained about.
 */
public class ComplaintToThisUserAlreadyExistException extends RuntimeException {

    public ComplaintToThisUserAlreadyExistException(Long userId, Long toUserId) {
        super("User with id: %d already sent complaint to user with id: %d".formatted(userId, toUserId));
    }
}