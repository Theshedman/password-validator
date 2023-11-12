package com.shedrack.validator;

import java.util.List;

public class DigitValidator extends PasswordValidator {

    public DigitValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfDigitsIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " digit(s)";

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
