package com.shedrack.validator;

import java.util.List;

public class UpperCaseValidator implements PasswordValidator {

    private final int upperCaseCount;

    public UpperCaseValidator(int upperCaseCount) {

        this.upperCaseCount = upperCaseCount;
    }

    @Override
    public int value() {

        return upperCaseCount;
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfUpperCaseLettersIn(password) < upperCaseCount) {

            String message = "must contain at least " + upperCaseCount + " uppercase letters";

            return new ValidationResult(false, List.of(message));
        }

        return new ValidationResult(true, List.of());

    }

    private static long numberOfUpperCaseLettersIn(String password) {

        return password
                .chars()
                .filter(Character::isUpperCase)
                .count();
    }
}
