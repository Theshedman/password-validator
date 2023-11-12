package com.shedrack.validator;

import java.util.List;

public class SpecialCharacterValidator extends PasswordValidator {

    public SpecialCharacterValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfSpecialCharactersIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " special character(s)";

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
