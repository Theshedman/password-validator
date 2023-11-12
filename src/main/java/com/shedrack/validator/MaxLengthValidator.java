package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MaxLengthValidator extends PasswordValidator {

    public MaxLengthValidator(int value) {

        super(value);
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength > value()) {

            var message = "length cannot exceed required " + value() + " maximum characters.";

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
}
