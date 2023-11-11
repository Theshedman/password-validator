package com.shedrack.validator;

import java.util.List;

public class DigitValidator implements PasswordValidator {

    private final int digitCount;

    public DigitValidator(int digitCount) {

        this.digitCount = digitCount;
    }

    @Override
    public int value() {

        return digitCount;
    }

    @Override
    public ValidationResult validate(String password) {

        long passwordDigitCount = numberOfDigitsIn(password);

        if (passwordDigitCount < digitCount) {

            String message = "must contain at least " + digitCount + " digit(s)";

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
