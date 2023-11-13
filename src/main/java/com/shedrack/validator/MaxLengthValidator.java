package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

/**
 * The MaxLengthValidator class validates if the length of a password exceeds a specified maximum length.
 */
public class MaxLengthValidator extends PasswordValidator {

    private int accumulatedLength = 0;

    public MaxLengthValidator(int passwordRule) {

        super(ValidatorCategory.TOTAL_LENGTH_LIMITER, passwordRule);
    }

    /**
     * Validates a password based on the maximum length rule.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength > passwordRule()) {

            var message = "length cannot exceed required " + passwordRule() + " maximum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, List.of());
    }

    /**
     * Determines if there is a conflict between the current instance of PasswordValidator and another validator.
     *
     * @param validator the other validator to check for conflicts with
     * @return an Optional containing a message describing the conflict if one exists, otherwise an empty Optional
     */
    @Override
    public Optional<String> conflictsWith(PasswordValidator validator) {

        return switch (validator.category()) {

            case LENGTH_EXPANDER -> handleLengthExpanderConflict(validator);

            case LENGTH_MINIMIZER -> handleLengthMinimizerConflict(validator);

            case PATTERN_ANALYZER, TOTAL_LENGTH_LIMITER -> noConflict();
        };
    }

    private Optional<String> handleLengthExpanderConflict(PasswordValidator validator) {

        accumulatedLength += validator.passwordRule();

        return verifyMaxLength();
    }

    private Optional<String> handleLengthMinimizerConflict(PasswordValidator validator) {

        if (this.passwordRule() < validator.passwordRule()) {

            return Optional.of("Invalid: Max length less than Min length.");
        }

        return Optional.empty();
    }

    private Optional<String> verifyMaxLength() {

        if (accumulatedLength > passwordRule()) {

            String message = "Invalid: Password length doesn't comply with the rules.";

            return Optional.of(message);
        }

        return Optional.empty();
    }
}
