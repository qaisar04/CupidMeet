package ru.polskiy.feedbackservice.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.polskiy.feedbackservice.exception.ComplaintToThisUserAlreadyExistException;
import ru.polskiy.feedbackservice.exception.CreateComplaintException;
import ru.polskiy.feedbackservice.exception.CreateFeedbackException;
import ru.polskiy.feedbackservice.exception.ThisFeedbackAlreadyExistException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ComplaintToThisUserAlreadyExistException.class, ThisFeedbackAlreadyExistException.class})
    ProblemDetail handleThisEntityAlreadyExistException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({CreateComplaintException.class, CreateFeedbackException.class})
    ProblemDetail handleCreateEntityException(RuntimeException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
