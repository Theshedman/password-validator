package com.shedrack.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PasswordValidatorManager implements ValidatorManager {

    private final Set<PasswordValidator> registeredValidators = new HashSet<>();

    @Override
    public void register(List<? extends PasswordValidator> newValidators)
            throws PasswordValidationConflictException {

        addAndValidateValidators(newValidators);
    }

    private void addAndValidateValidators(List<? extends PasswordValidator> newValidators) {

        registeredValidators.addAll(newValidators);

        for (var newValidator : newValidators) {

            for (var registeredValidator : registeredValidators) {

                var conflictsMsg = newValidator.conflictsWith(registeredValidator);

                if (conflictsMsg.isPresent()) {

                    throw new PasswordValidationConflictException(conflictsMsg.get());
                }
            }
        }
    }

    public ValidationResult validate(String password) {

        int invalidCount = 0;

        List<String> validationMessage = new ArrayList<>();

        for (var validator : registeredValidators) {

            ValidationResult result = validator.validate(password);

            if (!result.isValid()) {

                invalidCount++;

                validationMessage.addAll(result.message());
            }
        }

        var isValid = invalidCount > 0 ? Boolean.FALSE : Boolean.TRUE;

        return new ValidationResult(isValid, validationMessage);
    }
}
