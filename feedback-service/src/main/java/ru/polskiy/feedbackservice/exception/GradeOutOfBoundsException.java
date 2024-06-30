package ru.polskiy.feedbackservice.exception;

public class GradeOutOfBoundsException extends RuntimeException{

    public GradeOutOfBoundsException(String message) {
        super(message+" is out of range between 1 and 5");
    }
}
