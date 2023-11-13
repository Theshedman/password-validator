package com.shedrack.validator;

/**
 * This enumeration categorizes the different types of password validators based on their
 * operational significance and effect on password structure and length.
 * <p>
 * <ul>
 *     <li>{@link #LENGTH_EXPANDER} - Refers to validator rules that extend the
 *     maximum permissible length of a password, mainly via inclusion of specific character classes.</li>
 *     <li>{@link #LENGTH_MINIMIZER} - Refers to validator rules that establish a minimum limit on
 *     password length, without contributing to maximum password length.</li>
 *     <li>{@link #PATTERN_ANALYZER} - Pertains to validator rules aimed at pattern detection within
 *     the password, having no direct correlation with length metrics.</li>
 *     <li>{@link #TOTAL_LENGTH_LIMITER} - Applies to validator rules that specify a maximum threshold for
 *     password length.</li>
 * </ul>
 */
public enum ValidatorCategory {

    /**
     * The Length Expander category consists of validator rules that enhance
     * the maximum password length. Such rules typically mandate the inclusion of a specified
     * number of certain characters classes (e.g., uppercase letters, digits, special characters, etc.).
     */
    LENGTH_EXPANDER,

    /**
     * The Length Minimizer category includes validator rules that enforce a
     * minimum length for passwords, but do not have any impact on the maximum permissible
     * password length. An example of this category would be a minimum password length validator.
     */
    LENGTH_MINIMIZER,

    /**
     * The Pattern Analyzer category is comprised of validator rules that inspect
     * password content for specific patterns or sequences. These rules are not directly
     * connected to length requirements, and primarily focus on prohibiting repetition
     * of characters or certain character sequences.
     */
    PATTERN_ANALYZER,

    /**
     * The Total Length Limiter category includes the validator rule that sets a cap on
     * the total length of the password. It has the highest precedence amongst all
     * validators and establishes an unmodifiable upper limit on password length.
     * The length defined by this validator rule remains constant and cannot be
     * affected or altered by any other password validation rules.
     * <br />
     * Any length expanding rule will be ineffectual if it attempts to increase
     * the total password length beyond the limit set by this rule. This ensures
     * strict enforcement of a maximum length threshold for every password.
     */
    TOTAL_LENGTH_LIMITER
}
