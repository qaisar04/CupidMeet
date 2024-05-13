package kz.baltabayev.locationdataservice.controller.advice;

import kz.baltabayev.locationdataservice.exception.LocationServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is a global exception handler for the application.
 * It catches exceptions thrown throughout the application and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method is an exception handler for LocationServiceException.
     * When a LocationServiceException is thrown, this method will be invoked to handle it.
     * It returns a ProblemDetail object with the status set to BAD_REQUEST and the detail set to the exception's message.
     *
     * @param exception The LocationServiceException that was thrown.
     * @return A ProblemDetail object with the status set to BAD_REQUEST and the detail set to the exception's message.
     */
    @ExceptionHandler(LocationServiceException.class)
    ProblemDetail handlerLocationServiceException(LocationServiceException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}