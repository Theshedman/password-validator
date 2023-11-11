package com.shedrack.validator;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidatorManager implements ValidatorManager {
    private final List<PasswordValidator> registeredValidators = new ArrayList<>();

    @Override
    public void register(List<? extends PasswordValidator> newValidators)
            throws PasswordValidationConflictException {

        for (var newValidator : newValidators) {

            for (var registeredValidator : registeredValidators) {
                var conflictsMsg = newValidator.conflictsWith(registeredValidator);

                if (conflictsMsg.isPresent()) {
                    throw new PasswordValidationConflictException(conflictsMsg.get());
                }
            }

            registeredValidators.add(newValidator);
        }
    }

    public ValidationResult validate(String password) {

        return registeredValidators.getFirst().validate(password);
    }
}
