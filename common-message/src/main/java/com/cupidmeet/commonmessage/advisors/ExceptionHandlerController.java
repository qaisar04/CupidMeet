package com.cupidmeet.commonmessage.advisors;

import com.cupidmeet.commonmessage.commonresponse.CommonResponse;
import com.cupidmeet.commonmessage.exception.CommonException;
import com.cupidmeet.commonmessage.exception.CommonRuntimeException;
import com.cupidmeet.commonmessage.message.Message;
import com.cupidmeet.commonmessage.message.MessageLevel;
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

    @ExceptionHandler(value = CommonException.class)
    protected ResponseEntity<CommonResponse> handleException(final CommonException exception, final WebRequest request) {
        return new ResponseEntity<>(new CommonResponse(exception), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = CommonRuntimeException.class)
    protected ResponseEntity<CommonResponse> handleException(final CommonRuntimeException exception, final WebRequest request) {
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