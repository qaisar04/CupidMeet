package kz.baltabayev.userdetailsservice.exception;

/**
 * Exception thrown when an authentication request is rejected because the credentials are invalid.
 * This exception indicates that the user has failed to provide valid credentials to access a specific resource or operation.
 */
public class UnauthorizedException extends RuntimeException {
    /**
     * Constructs a new UnauthorizedException with the specified detail message.
     * The message is used to provide more information about the reason for the exception.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
     */
    public UnauthorizedException(String message) {
        super(message);
    }
}