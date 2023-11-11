package com.shedrack.validator;

import java.util.List;

public class SpecialCharacterValidator implements PasswordValidator {

    private final int numSpecialChars;

    public SpecialCharacterValidator(int numSpecialChars) {
        this.numSpecialChars = numSpecialChars;
    }

    @Override
    public int value() {
        return numSpecialChars;
    }

    @Override
    public ValidationResult validate(String password) {

        long specialCharacterCount = countSpecialCharactersIn(password);

        if (specialCharacterCount < numSpecialChars) {

            return new ValidationResult(false, List.of("must contain at least " + numSpecialChars + " special character(s)"));
        }

        return new ValidationResult(true, List.of());
    }

    private static long countSpecialCharactersIn(String password) {

        return password
                .chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .count();
    }
}
