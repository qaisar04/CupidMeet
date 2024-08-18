package com.cupidmeet.userdetailsservice.handler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключения EntityNotFoundException.
     *
     * @param ex Исключение, вызванное отсутствием сущности.
     * @return Ответ с кодом 404 и сообщением об ошибке.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleIllegalArgumentException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Обрабатывает исключения IllegalArgumentException.
     *
     * @param ex Исключение, вызванное неверными аргументами.
     * @return Ответ с кодом 400 и сообщением об ошибке.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequestException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Обрабатывает ошибки валидации аргументов метода.
     *
     * @param ex Исключение, вызванное неверной валидацией.
     * @return Ответ с кодом 400 и списком ошибок валидации.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
