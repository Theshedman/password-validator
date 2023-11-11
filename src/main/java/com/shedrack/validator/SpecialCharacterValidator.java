package com.shedrack.validator;

import java.util.List;

public class SpecialCharacterValidator implements PasswordValidator {

    private final int minSpecialChars;

    public SpecialCharacterValidator(int minSpecialChars) {
        this.minSpecialChars = minSpecialChars;
    }

    @Override
    public int value() {
        return minSpecialChars;
    }

    @Override
    public ValidationResult validate(String password) {

        if (numberOfSpecialCharactersIn(password) < minSpecialChars) {

            return new ValidationResult(false, List.of("must contain at least " + minSpecialChars + " special character(s)"));
        }

        return new ValidationResult(true, List.of());
    }

    private static long numberOfSpecialCharactersIn(String password) {

        return password
                .chars()
                .filter(c -> !Character.isLetterOrDigit(c))
                .count();
    }
}
