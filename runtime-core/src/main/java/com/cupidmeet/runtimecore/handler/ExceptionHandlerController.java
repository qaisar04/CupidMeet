package com.cupidmeet.runtimecore.handler;

import com.cupidmeet.runtimecore.response.CommonResponse;
import com.cupidmeet.runtimecore.exception.CustomException;
import com.cupidmeet.runtimecore.exception.CustomRuntimeException;
import com.cupidmeet.runtimecore.message.Message;
import com.cupidmeet.runtimecore.message.MessageLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = CustomException.class)
    protected ResponseEntity<CommonResponse> handleException(final CustomException exception, final WebRequest request) {
        return new ResponseEntity<>(new CommonResponse(exception), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CustomRuntimeException.class)
    protected ResponseEntity<CommonResponse> handleException(final CustomRuntimeException exception, final WebRequest request) {
        return new ResponseEntity<>(new CommonResponse(exception), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Message> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            messages.add(new Message(Messages.VALIDATION_ERROR, MessageLevel.ERROR, fieldName, errorMessage));
        });
        return new ResponseEntity<>(new CommonResponse(messages), headers, status);
    }
}