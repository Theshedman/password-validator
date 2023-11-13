package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

/**
 * The MinLengthValidator class validates a password based on the minimum length rule specified during instantiation.
 */
public class MinLengthValidator extends PasswordValidator {

    public MinLengthValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_MINIMIZER, passwordRule);
    }

    /**
     * Validates a password based on the minimum length rule.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength < passwordRule()) {

            var message = "length cannot be less than required " + passwordRule() + " minimum characters.";

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

            case TOTAL_LENGTH_LIMITER -> handleTotalLengthLimiterConflict(validator);

            case PATTERN_ANALYZER, LENGTH_EXPANDER, LENGTH_MINIMIZER -> noConflict();
        };
    }

    private Optional<String> handleTotalLengthLimiterConflict(PasswordValidator validator) {

        if (this.passwordRule() > validator.passwordRule()){

            return Optional.of("Invalid: Min length exceeds max length.");
        }

        return Optional.empty();
    }
}
