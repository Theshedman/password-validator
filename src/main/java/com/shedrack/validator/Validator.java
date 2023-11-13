package com.shedrack.validator;

/**
 * The Validator interface defines a contract for validating passwords. Implementing classes must provide
 * an implementation for the validate() method, which takes a password as input and returns a ValidationResult.
 */
public interface Validator {

    /**
     * Validates a password based on the given rules and returns the validation result.
     *
     * @param password the password to be validated
     * @return the validation result which includes whether the password is valid and a list of validation messages
     */
    ValidationResult validate(String password);

}
