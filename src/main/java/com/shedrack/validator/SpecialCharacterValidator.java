package com.shedrack.validator;

import java.util.List;

/**
 * SpecialCharacterValidator validates a password
 * by checking the number of special characters present. It extends the functionality
 * of the PasswordValidator class by adding a specific validation rule for special characters.
 */
public class SpecialCharacterValidator extends PasswordValidator {

    public SpecialCharacterValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
    }

    /**
     * Validates a password by checking the number of special characters present.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    @Override
    public ValidationResult validate(String password) {

        if (numberOfSpecialCharactersIn(password) < passwordRule()) {

            String message = "must contain at least " + passwordRule() + " special character(s)";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, List.of());
    }

    private static long numberOfSpecialCharactersIn(String password) {

        return password
                .chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .count();
    }
}
