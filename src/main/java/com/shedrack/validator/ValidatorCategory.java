package com.shedrack.validator;

/**
 * This enumeration is used to classify password validator types
 * based on their impact on password length.
 * <ul>
 *     <li>{@link #LENGTH_EXPANDER} - the validator rule increases the maximum length of password.</li>
 *     <li>{@link #LENGTH_MINIMIZER} - the validator rule imposes a minimum length restriction, does not contribute to expanding the max length.</li>
 *     <li>{@link #PATTERN_ANALYZER} - the validator rule checks for patterns in password, not related to length.</li>
 * </ul>
 */
public enum ValidatorCategory {

    /**
     * The Length Expander type represents validators that increase
     * the max length of the password. This would apply to rules such
     * as requiring a certain count of a type of characters
     * (like special characters, uppercase, digits, etc.).
     */
    LENGTH_EXPANDER,

    /**
     * The Length Minimizer type represents validators that impose
     * a minimum length restriction but do not contribute to
     * expanding the max length, like a MinLengthValidator.
     */
    LENGTH_MINIMIZER,

    /**
     * The Pattern Analyzer type represents validators that check
     * for patterns or sequences in the password, but do not directly
     * related to the length requirements. This could be, verifying no
     * repeated characters or prohibiting certain character sequences.
     */
    PATTERN_ANALYZER
}
