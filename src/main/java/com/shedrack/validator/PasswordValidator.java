package com.shedrack.validator;

import java.util.Optional;

public abstract class PasswordValidator implements Validator {

    private final int value;

    protected PasswordValidator(int value) {

        assertNonNegative(value);

        this.value = value;

    }

    protected int value() {

        return value;
    }

    private void assertNonNegative(int value) {

        if (value < 0) {

            throw new NegativeValidatorValueException(
                    "Invalid Input: Negative values not allowed."
            );
        }
    }

    protected Optional<String> conflictsWith(PasswordValidator validator) {

        return Optional.empty();
    }
}
