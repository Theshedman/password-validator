package com.shedrack.validator;

import java.util.List;

public class LowerCaseValidator extends PasswordValidator {

    public LowerCaseValidator(int value) {

        super(value);

    }

    private static long numberOfLowerCaseLettersIn(String password) {

        return password
                .chars()
                .filter(Character::isLowerCase)
                .count();
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfLowerCaseLettersIn(password) < value()) {

            String message = "must contain at least " + value() + " lowercase letters";

            return new ValidationResult(false, List.of(message));
        }

        return new ValidationResult(true, List.of());

    }
}
