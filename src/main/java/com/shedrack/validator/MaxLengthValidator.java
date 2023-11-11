package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MaxLengthValidator implements PasswordValidator {

    private final int maxLength;

    public MaxLengthValidator(int maxLength) {

        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength > maxLength) {

            var message = "length cannot exceed required " + maxLength + " maximum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

    @Override
    public Optional<String> conflictsWith(PasswordValidator validator) {

        if (validator instanceof MinLengthValidator minLengthValidator) {

            if (this.value() < minLengthValidator.value()){

                return Optional.of("Invalid: Max length less than Min length.");
            }
        }

        return Optional.empty();
    }

    @Override
    public int value() {

        return maxLength;
    }
}
