package com.shedrack.validator;

public class NegativeValidatorValueException extends PasswordValidationException {

    public NegativeValidatorValueException(String message) {

        super(message);
    }
}
