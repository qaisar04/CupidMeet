package kz.baltabayev.storageservice.advice;

import jakarta.persistence.EntityNotFoundException;
import kz.baltabayev.storageservice.exception.FileDeletionException;
import kz.baltabayev.storageservice.exception.S3ResourceNotFoundException;
import kz.baltabayev.storageservice.exception.WritingToStorageFailsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler responsible for handling all the exceptions throwing in the Storage
 * microservice
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    private static final String EXCEPTION_CAUSE_PREFIX = "Exception has cause: ";
    private static final String EXCEPTION_DESCRIPTION_PREFIX = "Exception occurred while processing request: ";

    /**
     * Exception handler for not found exceptions. Returns a response with a status code of 404
     * (NOT_FOUND) and a response body containing the error message from the exception.
     *
     * @param exception the exception that was caught
     * @return a {@link ResponseEntity} with a status code of 404 and an error message
     */
    @ExceptionHandler({S3ResourceNotFoundException.class, EntityNotFoundException.class})
    protected ResponseEntity<String> notFoundHandle(Exception exception) {
        log.warn(EXCEPTION_CAUSE_PREFIX, exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Exception handler for unsupported media type exceptions. Returns a response with a status code
     * of 415 (UNSUPPORTED_MEDIA_TYPE) and a response body containing the error message from the
     * exception.
     *
     * @param exception the exception that was caught
     * @return a {@link ResponseEntity} with a status code of 415 and an error message
     */
    @ExceptionHandler({InvalidMediaTypeException.class})
    protected ResponseEntity<String> unsupportedMediaTypeHandle(Exception exception) {
        log.warn(EXCEPTION_CAUSE_PREFIX, exception);
        return ResponseEntity
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .body(exception.getMessage());
    }

    /**
     * Method handles WritingToStorageFailsException, throwing when error happens when writing file to
     * S3 storage
     *
     * @param exception WritingToStorageFailsException
     * @return error message
     */
    @ExceptionHandler(WritingToStorageFailsException.class)
    public ResponseEntity<String> handleWritingToStorageFailsException(
            WritingToStorageFailsException exception) {
        log.error(EXCEPTION_DESCRIPTION_PREFIX, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Method handles FileDeletionFailedException, thrown when an error occurs while deleting a file.
     *
     * @param exception FileDeletionFailedException
     * @return error message
     */
    @ExceptionHandler(FileDeletionException.class)
    public ResponseEntity<String> handleFileDeletionException(
            FileDeletionException exception) {
        log.error(EXCEPTION_DESCRIPTION_PREFIX, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Method handles any other exception, throwing while application running
     *
     * @param exception exception
     * @return error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleCommonException(Exception exception) {
        log.error(EXCEPTION_DESCRIPTION_PREFIX, exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}