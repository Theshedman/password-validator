package com.shedrack.validator;

import java.util.List;

/**
 * LowerCaseValidator validates a password based on
 * the number of lowercase letters it contains.
 */
public class LowerCaseValidator extends PasswordValidator {

    public LowerCaseValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);

    }

    private static long numberOfLowerCaseLettersIn(String password) {

        return password
                .chars()
                .filter(Character::isLowerCase)
                .count();
    }

    /**
     * Validates a password based on the number of lowercase letters it contains.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    @Override
    public ValidationResult validate(String password) {

        if (numberOfLowerCaseLettersIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " lowercase letters";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, List.of());

    }
}
