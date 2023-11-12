package com.shedrack.validator;

public class PasswordValidationException extends IllegalArgumentException {

    public PasswordValidationException(String message) {

        super(message);
    }
}
