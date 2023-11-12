package com.shedrack.validator;

import java.util.List;

public class UpperCaseValidator extends PasswordValidator {

    public UpperCaseValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfUpperCaseLettersIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " uppercase letters";

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
