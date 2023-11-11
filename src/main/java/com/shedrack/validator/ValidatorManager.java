package com.shedrack.validator;

import java.util.List;

public interface ValidatorManager extends Validator {

    default void register(PasswordValidator validator) {

        register(List.of(validator));
    }

    default void register(PasswordValidator... validators) {

        register(List.of(validators));
    }

    void register(List<? extends PasswordValidator> validators)
            throws PasswordValidationConflictException;
}
