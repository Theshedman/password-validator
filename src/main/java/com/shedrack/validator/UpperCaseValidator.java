package com.shedrack.validator;

import java.util.List;

/**
 * The UpperCaseValidator validates a password based on
 * the number of uppercase letters it contains.
 */
public class UpperCaseValidator extends PasswordValidator {

    public UpperCaseValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    /**
     * Validates a password based on the number of uppercase letters it contains.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid
     *         and a list of validation messages, if any
     */
    @Override
    public ValidationResult validate(String password) {

        if (numberOfUpperCaseLettersIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " uppercase letters";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, List.of());

    }

    private static long numberOfUpperCaseLettersIn(String password) {

        return password
                .chars()
                .filter(Character::isUpperCase)
                .count();
    }
}
