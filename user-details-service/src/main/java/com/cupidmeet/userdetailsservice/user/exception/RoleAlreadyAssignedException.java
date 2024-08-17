package com.cupidmeet.userdetailsservice.user.exception;

public class RoleAlreadyAssignedException extends RuntimeException {

    public RoleAlreadyAssignedException(String message) {
        super(message);
    }
}