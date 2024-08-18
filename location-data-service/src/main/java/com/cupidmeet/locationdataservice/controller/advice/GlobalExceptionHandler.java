package com.cupidmeet.locationdataservice.controller.advice;

import com.cupidmeet.locationdataservice.exception.LocationServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LocationServiceException.class)
    public ResponseEntity<String> handlerLocationServiceException(LocationServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}