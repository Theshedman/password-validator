package com.shedrack.validator;

public class PasswordValidationConflictException extends IllegalArgumentException {

    public PasswordValidationConflictException(String message) {

        super(message);
    }
}
