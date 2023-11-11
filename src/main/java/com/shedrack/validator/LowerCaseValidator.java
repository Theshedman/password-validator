package com.shedrack.validator;

import java.util.List;

public class LowerCaseValidator implements PasswordValidator {

    private final int lowerCaseCount;

    public LowerCaseValidator(int lowerCaseCount) {

        this.lowerCaseCount = lowerCaseCount;
    }

    private static long numberOfLowerCaseLettersIn(String password) {

        return password
                .chars()
                .filter(Character::isLowerCase)
                .count();
    }

    @Override
    public int value() {

        return lowerCaseCount;
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfLowerCaseLettersIn(password) < lowerCaseCount) {

            String message = "must contain at least " + lowerCaseCount + " lowercase letters";

            return new ValidationResult(false, List.of(message));
        }

        return new ValidationResult(true, List.of());

    }
}
