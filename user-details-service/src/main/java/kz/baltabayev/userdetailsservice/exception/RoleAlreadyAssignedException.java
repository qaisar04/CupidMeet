package kz.baltabayev.userdetailsservice.exception;

/**
 * Exception thrown when an attempt is made to assign a role to a user
 * who already has that role assigned.
 * This can help in preventing redundant operations on the database
 * and enforcing role assignment logic in the application.
 */
public class RoleAlreadyAssignedException extends RuntimeException {
    /**
     * Constructs a new RoleAlreadyAssignedException with the specified detail message.
     * The message can be used to provide more information about the exception.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
     */
    public RoleAlreadyAssignedException(String message) {
        super(message);
    }
}