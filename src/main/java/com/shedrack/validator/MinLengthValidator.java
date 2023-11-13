package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MinLengthValidator extends PasswordValidator {

    public MinLengthValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_MINIMIZER, passwordRule);
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength < passwordRule()) {

            var message = "length cannot be less than required " + passwordRule() + " minimum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

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
