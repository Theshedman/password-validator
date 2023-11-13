package com.shedrack.validator;

import java.util.Optional;

/**
 * PasswordValidator is an abstract class that implements the Validator interface.
 * It provides a basic implementation for password validation and categorization.
 * Subclasses of PasswordValidator can define their own validation rules and behavior.
 */
public abstract class PasswordValidator implements Validator {

    private final int rule;

    private final ValidatorCategory category;

    protected PasswordValidator(ValidatorCategory category, int rule) {

        assertNonNegative(rule);

        this.rule = rule;
        this.category = category;

    }

    /**
     * Retrieves the password rule for this PasswordValidator.
     *
     * @return the password rule
     */
    protected int passwordRule() {

        return rule;
    }

    /**
     * Returns the category of this password validator.
     *
     * @return the category of this password validator
     */
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

    /**
     * Determines if there is a conflict between the current instance of PasswordValidator and another validator.
     *
     * @param validator the other validator to check for conflicts with
     * @return an Optional containing a message describing the conflict if one exists, otherwise an empty Optional
     */
    protected Optional<String> conflictsWith(PasswordValidator validator) {

        return Optional.empty();
    }

    protected Optional<String> noConflict() {

        return Optional.empty();
    }
}
