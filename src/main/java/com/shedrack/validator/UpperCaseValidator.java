package com.shedrack.validator;

import java.util.List;

public class UpperCaseValidator extends PasswordValidator {

    public UpperCaseValidator(int value) {

        super(value);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfUpperCaseLettersIn(password) < value()) {

            String message = "must contain at least " + value() + " uppercase letters";

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
