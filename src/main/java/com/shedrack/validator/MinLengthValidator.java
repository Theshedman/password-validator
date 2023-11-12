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

        if (validator instanceof MaxLengthValidator maxLengthValidator) {

            if (this.passwordRule() > maxLengthValidator.passwordRule()){

                return Optional.of("Invalid: Min length exceeds max length.");
            }
        }

        return Optional.empty();
    }
}
