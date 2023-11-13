package com.shedrack.validator;

import java.util.List;

/**
 * The DigitValidator class validates passwords based on the number of digits
 * they contain.
 */
public class DigitValidator extends PasswordValidator {

    public DigitValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    /**
     * This method validates a password based on a specific rule, which is the number of digits the password must contain.
     *
     * @param password the password to be validated
     * @return the validation result, including whether the password is valid and a list of validation messages
     */
    @Override
    public ValidationResult validate(String password) {

        if (numberOfDigitsIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " digit(s)";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, List.of());
    }

    private static long numberOfDigitsIn(String password) {

        return password
                .chars()
                .filter(Character::isDigit)
                .count();
    }
}
