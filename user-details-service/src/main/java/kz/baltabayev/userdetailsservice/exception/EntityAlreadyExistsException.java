package kz.baltabayev.userdetailsservice.exception;

/**
 * This class represents a custom exception that is thrown when an entity already exists in the database.
 * It extends the RuntimeException class, which means it's an unchecked exception.
 * Unchecked exceptions are exceptions that can be avoided programmatically.
 * <br>
 * The class has a single constructor that accepts a message parameter. This message provides more details about the exception.
 * For example, it could be used to indicate the type of the entity that already exists and its identifier.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new EntityAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the Throwable.getMessage() method.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}