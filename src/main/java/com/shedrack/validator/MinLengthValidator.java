package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MinLengthValidator implements PasswordValidator {

    private final int minLength;

    public MinLengthValidator(int minLength) {

        this.minLength = minLength;
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength < minLength) {

            var message = "length cannot be less than required " + minLength + " minimum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

    @Override
    public Optional<String> conflictsWith(PasswordValidator validator) {

        if (validator instanceof MaxLengthValidator maxLengthValidator) {

            if (this.value() > maxLengthValidator.value()){

                return Optional.of("Invalid: Min length exceeds max length.");
            }
        }

        return Optional.empty();
    }

    @Override
    public int value() {

        return minLength;
    }
}
