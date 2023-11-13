package com.shedrack.validator;

import java.util.List;

/**
 * A manager interface for validating passwords against a set of registered password validators.
 * Extends the {@link Validator} interface.
 */
public interface ValidatorManager extends Validator {


    /**
     * Register a single PasswordValidator with the ValidatorManager.
     *
     * @param validator The PasswordValidator to register.
     */
    default void register(PasswordValidator validator) {

        register(List.of(validator));
    }

    /**
     * Registers the given PasswordValidators with the ValidatorManager.
     *
     * @param validators The PasswordValidators to register
     */
    default void register(PasswordValidator... validators) {

        register(List.of(validators));
    }

    /**
     * Registers a list of password validators.
     *
     * @param validators The list of password validators to register. The validators must extend the {@link PasswordValidator} class.
     * @throws PasswordValidationConflictException if there is a conflict between the registered validators.
     */
    void register(List<? extends PasswordValidator> validators)
            throws PasswordValidationConflictException;
}
