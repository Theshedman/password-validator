package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MaxLengthValidator extends PasswordValidator {

    private int totalPasswordLength = 0;

    public MaxLengthValidator(int passwordRule) {

        super(ValidatorCategory.LENGTH_EXPANDER, passwordRule);
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

        return validatePasswordRulesAgainstMaxLength(validator);
    }

    private Optional<String> validatePasswordRulesAgainstMaxLength(PasswordValidator validator) {

        switch (validator.category()) {

            case LENGTH_EXPANDER -> {

                if (!(validator instanceof MaxLengthValidator)) {

                    totalPasswordLength += validator.passwordRule();
                }
            }

            case LENGTH_MINIMIZER -> {

                if (this.passwordRule() < validator.passwordRule()) {

                    return Optional.of("Invalid: Max length less than Min length.");
                }
            }

            case PATTERN_ANALYZER -> {}
        }

        if (totalPasswordLength > passwordRule()) {

            String message = "Invalid: Password length doesn't comply with the rules.";

            return Optional.of(message);
        }

        return Optional.empty();
    }
}
