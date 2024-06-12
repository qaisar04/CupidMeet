package kz.baltabayev.userdetailsservice.controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for handling {@link EntityNotFoundException}.
 * This class provides a centralized way to handle entity not found exceptions
 * and convert them into {@link ProblemDetail} responses with appropriate HTTP status codes.
 */
public class GlobalExceptionHandler {

    /**
     * Handles {@link EntityNotFoundException} and converts it into a {@link ProblemDetail} response with HTTP status 404 (Not Found).
     *
     * @param exception the exception to handle
     * @return a {@link ProblemDetail} response with the HTTP status 404 (Not Found) and the exception message as the detail
     */
    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleGradeNotFoundException(EntityNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}