package com.shedrack.validator;

import java.util.Optional;

public abstract class PasswordValidator implements Validator {

    private final int rule;

    private final ValidatorCategory category;

    protected PasswordValidator(ValidatorCategory category, int rule) {

        assertNonNegative(rule);

        this.rule = rule;
        this.category = category;

    }

    protected int passwordRule() {

        return rule;
    }

    protected ValidatorCategory category() {

        return category;
    }

    private void assertNonNegative(int rule) {

        if (rule < 0) {

            throw new NegativeValidatorValueException(
                    "Invalid Input: Negative rule not allowed."
            );
        }
    }

    protected Optional<String> conflictsWith(PasswordValidator validator) {

        return Optional.empty();
    }

    protected Optional<String> noConflict() {

        return Optional.empty();
    }
}
