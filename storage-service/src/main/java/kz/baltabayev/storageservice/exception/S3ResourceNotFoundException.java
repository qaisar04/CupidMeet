package kz.baltabayev.storageservice.exception;

public class S3ResourceNotFoundException extends RuntimeException {

    public S3ResourceNotFoundException(String message) {
        super(message);
    }
}