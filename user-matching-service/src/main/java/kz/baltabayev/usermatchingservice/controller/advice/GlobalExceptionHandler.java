package kz.baltabayev.usermatchingservice.controller.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for handling {@link EntityNotFoundException}.
 * This class provides a centralized way to handle entity not found exceptions
 * and convert them into {@link ProblemDetail} responses with appropriate HTTP status codes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link EntityNotFoundException} exceptions.
     *
     * @param ex The caught exception.
     * @return A ResponseEntity with HTTP status code of 404 (NOT_FOUND) and a body containing a
     * message indicating the invalid request parameters.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleIllegalArgumentException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Invalid request parameters: " + ex.getMessage());
    }

    /**
     * Exception handler for handling bad request exceptions.
     *
     * @param ex The {@link Exception} to be handled.
     * @return The {@link ResponseEntity} with the error message in the body and a status of
     * {@link HttpStatus#BAD_REQUEST}.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handles {@link MethodArgumentNotValidException} exceptions.
     *
     * @param ex The caught exception.
     * @return A ResponseEntity with HTTP status code of 400 (BAD_REQUEST) and a body containing
     * detailed validation error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
