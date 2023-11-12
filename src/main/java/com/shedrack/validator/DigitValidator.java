package com.shedrack.validator;

import java.util.List;

public class DigitValidator extends PasswordValidator {

    public DigitValidator(int value) {

        super(value);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfDigitsIn(password) < value()) {

            String message = "must contain at least " + value() + " digit(s)";

            return new ValidationResult(false, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

    private static long numberOfDigitsIn(String password) {

        return password
                .chars()
                .filter(Character::isDigit)
                .count();
    }
}
