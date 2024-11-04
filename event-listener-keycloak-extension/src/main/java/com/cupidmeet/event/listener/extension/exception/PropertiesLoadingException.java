package com.cupidmeet.event.listener.extension.exception;

/**
 * This class represents a custom exception that is thrown when there is an error loading properties.
 * It extends the Exception class and includes a constructor that accepts a message parameter.
 * The message is prefixed with "Error loading properties: " to provide more context about the error.
 */
public class PropertiesLoadingException extends RuntimeException {

    /**
     * Constructs a new PropertiesLoadingException with the specified detail message.
     * The detail message is saved for later retrieval by the Throwable.getMessage() method.
     *
     * @param message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
     */
    public PropertiesLoadingException(String message) {
        super("Error loading properties: " + message);
    }
}