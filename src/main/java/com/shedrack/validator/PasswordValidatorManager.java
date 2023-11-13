package com.shedrack.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The PasswordValidatorManager class implements the ValidatorManager interface
 * to provide the functionality of validating passwords based on a set of registered validators.
 */
public class PasswordValidatorManager implements ValidatorManager {

    private final Set<PasswordValidator> registeredValidators = new HashSet<>();

    private static void checkForConflictsAmongNewValidators
            (List<? extends PasswordValidator> newValidators) {

        // First check new validators amongst themselves for conflicts
        for (int i = 0; i < newValidators.size(); i++) {

            for (int j = i + 1; j < newValidators.size(); j++) {

                var conflictsMsg = newValidators.get(i).conflictsWith(newValidators.get(j));

                if (conflictsMsg.isPresent()) {

                    throw new PasswordValidationConflictException(conflictsMsg.get());
                }
            }
        }
    }

    /**
     * Registers a list of password validators.
     *
     * @param newValidators The list of password validators to register. The validators must extend the {@link PasswordValidator} class.
     * @throws PasswordValidationConflictException if there is a conflict between the registered validators.
     */
    @Override
    public void register(List<? extends PasswordValidator> newValidators)
            throws PasswordValidationConflictException {

        checkForConflictsAmongNewValidators(newValidators);

        validateAndAddNewValidators(newValidators);
    }

    private void validateAndAddNewValidators
            (List<? extends PasswordValidator> newValidators) {

        // Add all new validators to registered validators and check for conflicts
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

    /**
     * Validates a password based on the given rules and returns the validation result.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    public ValidationResult validate(String password) {

        int invalidCount = 0;

        List<String> validationMessages = new ArrayList<>();

        for (var validator : registeredValidators) {

            ValidationResult result = validator.validate(password);

            if (result.isValid()) continue;

            invalidCount++;

            validationMessages.addAll(result.messages());
        }

        var isValid = invalidCount == 0;

        return new ValidationResult(isValid, validationMessages);
    }
}
