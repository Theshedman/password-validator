package com.shedrack.validator;

import java.util.Optional;

public interface PasswordValidator extends Validator {

    int value();

    Optional<String> conflictsWith(PasswordValidator validator);
}
