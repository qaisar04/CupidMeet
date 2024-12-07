package com.cupidmeet.event.listener.extension.exception;

/**
 * Этот класс представляет исключение, возникающее при ошибке загрузки свойств.
 */
public class PropertiesLoadingException extends RuntimeException {

    /**
     * Конструктор для исключения PropertiesLoadingException.
     *
     * @param message сообщение об ошибке
     */
    public PropertiesLoadingException(String message) {
        super("Error loading properties: " + message);
    }
}