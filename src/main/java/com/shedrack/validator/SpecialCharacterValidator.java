package com.shedrack.validator;

import java.util.List;

public class SpecialCharacterValidator extends PasswordValidator {

    public SpecialCharacterValidator(int value) {

        super(value);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfSpecialCharactersIn(password) < value()) {

            String message = "must contain at least " + value() + " special character(s)";

            return new ValidationResult(false, List.of(message));
        }

        return new ValidationResult(true, List.of());
    }

    private static long numberOfSpecialCharactersIn(String password) {

        return password
                .chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .count();
    }
}
