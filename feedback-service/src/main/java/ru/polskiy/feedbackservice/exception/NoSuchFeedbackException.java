package ru.polskiy.feedbackservice.exception;

public class NoSuchFeedbackException extends RuntimeException{

    public NoSuchFeedbackException(String message) {
        super("This object:"+message+ " doesn't exist in database and can't be patched");
    }
}
