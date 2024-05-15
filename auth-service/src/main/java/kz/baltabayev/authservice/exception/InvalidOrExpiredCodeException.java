package kz.baltabayev.authservice.exception;

public class InvalidOrExpiredCodeException extends RuntimeException {

    public InvalidOrExpiredCodeException(String message) {
        super(message);
    }
}
