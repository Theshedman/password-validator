package com.shedrack.validator;

import java.util.Optional;

public interface PasswordValidator extends Validator {

    int value();

    default Optional<String> conflictsWith(PasswordValidator validator) {

        return Optional.empty();
    }
}
