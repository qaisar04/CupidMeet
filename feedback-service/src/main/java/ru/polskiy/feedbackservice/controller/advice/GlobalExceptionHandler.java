package ru.polskiy.feedbackservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.polskiy.feedbackservice.exception.*;

/**
 * Global exception handler for managing exceptions.
 * This controller advice handles various custom exceptions and returns appropriate HTTP statuses and error messages.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when complaint or feedback are already exists.
     *
     * @param exception The exception thrown when complaint or feedback  already exists.
     * @return A {@link ProblemDetail} object with HTTP status 409 (Conflict) and the exception message.
     */
    @ExceptionHandler({ComplaintToThisUserAlreadyExistException.class, ThisFeedbackAlreadyExistException.class})
    ProblemDetail handleThisEntityAlreadyExistException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }

    /**
     * Handles exceptions when there is an error creating an entity.
     *
     * @param exception The exception thrown during the creation of an entity.
     * @return A {@link ProblemDetail} object with HTTP status 400 (Bad Request) and the exception message.
     */
    @ExceptionHandler({CreateComplaintException.class, UpdateFeedbackException.class})
    ProblemDetail handleCreateEntityException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles exceptions when an entity is not found.
     *
     * @param exception The exception thrown when an entity is not found.
     * @return A {@link ProblemDetail} object with HTTP status 404 (Not Found) and the exception message.
     */
    @ExceptionHandler({NoSuchFeedbackException.class})
    ProblemDetail handleNoObjectException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}