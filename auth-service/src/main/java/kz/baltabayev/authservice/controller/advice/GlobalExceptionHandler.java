package kz.baltabayev.authservice.controller.advice;

import kz.baltabayev.authservice.exception.InvalidOrExpiredCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOrExpiredCodeException.class)
    ProblemDetail handleInvalidOrExpiredCodeException(InvalidOrExpiredCodeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }
}
