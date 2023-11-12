package com.shedrack.validator;

import java.util.List;
import java.util.Optional;

public class MaxLengthValidator extends PasswordValidator {

    private int verifier = 0;

    public MaxLengthValidator(int value) {

        super(value);
    }

    @Override
    public ValidationResult validate(String password) {

        var passwordLength = password.length();

        if (passwordLength > value()) {

            var message = "length cannot exceed required " + value() + " maximum characters.";

            return new ValidationResult(Boolean.FALSE, List.of(message));
        }

        return new ValidationResult(Boolean.TRUE, null);
    }

    @Override
    public Optional<String> conflictsWith(PasswordValidator validator) {

        return validatePasswordRulesAgainstMaxLength(validator);
    }

    private Optional<String> validatePasswordRulesAgainstMaxLength(PasswordValidator validator) {

        switch (validator) {

            case DigitValidator dtv -> verifier += dtv.value();

            case LowerCaseValidator lcv -> verifier += lcv.value();

            case UpperCaseValidator ucv -> verifier += ucv.value();

            case SpecialCharacterValidator scv -> verifier += scv.value();

            case MinLengthValidator miv -> {

                if (this.value() < miv.value()){

                    return Optional.of("Invalid: Max length less than Min length.");
                }
            }

            default -> { }
        }

        if (verifier > value()) {

            String message = "Invalid: Password length doesn't comply with the rules.";

            return Optional.of(message);
        }

        return Optional.empty();
    }
}
