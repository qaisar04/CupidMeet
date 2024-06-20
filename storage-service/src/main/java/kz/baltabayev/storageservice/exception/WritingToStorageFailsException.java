package kz.baltabayev.storageservice.exception;

import java.io.IOException;

/**
 * Exception throwing when error happens while writing business idea file to S3 storage.
 */
public class WritingToStorageFailsException extends RuntimeException {

    private static final String MESSAGE = "Error occurred when writing to storage";
    private final IOException cause;

    public WritingToStorageFailsException(IOException cause) {
        super(MESSAGE);
        this.cause = cause;
    }
}
