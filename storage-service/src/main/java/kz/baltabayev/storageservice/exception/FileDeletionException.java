package kz.baltabayev.storageservice.exception;

public class FileDeletionException extends RuntimeException {

    public FileDeletionException(String filePath) {
        super("Failed to delete the file at path: %s".formatted(filePath));
    }
}