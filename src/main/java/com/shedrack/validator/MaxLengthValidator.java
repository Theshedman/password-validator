package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MaxLengthValidator extends PasswordValidator {

    private int accumulatedLength = 0;

    public MaxLengthValidator(int passwordRule) {

        super(ValidatorCategory.TOTAL_LENGTH_LIMITER, passwordRule);
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength > passwordRule()) {

            var message = "length cannot exceed required " + passwordRule() + " maximum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

    @Override
    public Optional<String> conflictsWith(PasswordValidator validator) {

        return switch (validator.category()) {

            case LENGTH_EXPANDER -> handleLengthExpanderConflict(validator);

            case LENGTH_MINIMIZER -> handlesLengthMinimizerConflict(validator);

            case PATTERN_ANALYZER, TOTAL_LENGTH_LIMITER -> noConflict();
        };
    }

    private Optional<String> handleLengthExpanderConflict(PasswordValidator validator) {

        accumulatedLength += validator.passwordRule();

        return verifyMaxLength();
    }

    private Optional<String> handlesLengthMinimizerConflict(PasswordValidator validator) {

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
