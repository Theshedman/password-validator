package com.shedrack.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class PasswordValidatorTest extends PasswordValidatorTestData {

    private ValidatorManager passwordValidatorManager;

    @BeforeEach
    void setup() {

        passwordValidatorManager = new PasswordValidatorManager();
    }

    @Test
    @DisplayName("Should Return Validator Value")
    public void getValidatorValue() {

        PasswordValidator validator = new MinLengthValidator(6);
        var expectedValue = 6;

        assertThat(validator.value()).isEqualTo(expectedValue);
    }

    @ParameterizedTest(name = "[{index}] password={0}, minLength={1}, expected={2}")
    @MethodSource(value = "MinLengthValidatorData")
    @DisplayName("Should Not Be Less Than Min Length")
    public void minimumLengthValidation(String password, int minLength, boolean expected) {

        PasswordValidator minLengthValidator = new MinLengthValidator(minLength);
        passwordValidatorManager.register(minLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] password={0}, maxLength={1}, expected={2}")
    @MethodSource(value = "MaxLengthValidatorData")
    @DisplayName("Should Not Be Greater Than Max Length")
    public void maximumLengthValidation(String password, int maxLength, boolean expected) {

        PasswordValidator maxLengthValidator = new MaxLengthValidator(maxLength);
        passwordValidatorManager.register(maxLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] MinLength={0}, MaxLength={1}")
    @DisplayName("Should Not Conflict With Another Validator")
    @MethodSource(value = "ConflictValidationData")
    public void checkForConflict(int min, int max) {

        var minValidator = new MinLengthValidator(min);
        var maxValidator = new MaxLengthValidator(max);

        assertThatExceptionOfType(PasswordValidationConflictException.class)
                .isThrownBy(() -> passwordValidatorManager
                        .register(minValidator, maxValidator)
                );

    }

    @ParameterizedTest(name = "[{index}] password={0}, digitCount={1}, expected={2}")
    @DisplayName("Should Contain Digits")
    @MethodSource(value = "DigitValidationData")
    public void checkForDigits(String password, int digitCount, boolean expected) {

        DigitValidator digitValidator = new DigitValidator(digitCount);
        passwordValidatorManager.register(digitValidator);

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] password={0}, minLength={1}, maxLength={2}, digitCount={3}, expected={4}")
    @DisplayName("Should Validate Digits, Min And MaxLength")
    @MethodSource(value = "DigitMinAndMaxLengthValidationData")
    public void validateDigitMinAndMaxLength(
            String password, int minLength,
            int maxLength, int digitCount, boolean expected
    ) {

        var digitValidator = new DigitValidator(digitCount);
        var minLengthValidator = new MinLengthValidator(minLength);
        var maxLengthValidator = new MaxLengthValidator(maxLength);

        passwordValidatorManager
                .register(digitValidator, minLengthValidator, maxLengthValidator);

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] password={0}, numSpecialChars={1}, expected={2}")
    @DisplayName("Should Contain Special Characters")
    @MethodSource(value = "SpecialCharacterValidationData")
    public void checkForSpecialCharacters(
            String password, int numSpecialChars, boolean expected
    ) {

        var specialCharacterValidator = new SpecialCharacterValidator(numSpecialChars);
        passwordValidatorManager.register(specialCharacterValidator);

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] password={0}, upperCaseCount={1}, expected={2}")
    @DisplayName("Should Contain Uppercase letters")
    @MethodSource(value = "UpperCaseValidationData")
    public void checkForUpperCaseCharacters(
            String password, int upperCaseCount, boolean expected
    ) {

        var upperCaseValidator = new UpperCaseValidator(upperCaseCount);
        passwordValidatorManager.register(upperCaseValidator);

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(name = "[{index}] password={0}, lowerCaseCount={1}, expected={2}")
    @DisplayName("Should Contain Lowercase letters")
    @MethodSource(value = "LowerCaseValidationData")
    public void checkForLowerCaseCharacters(
            String password, int lowerCaseCount, boolean expected
    ) {

        var lowerCaseValidator = new LowerCaseValidator(lowerCaseCount);
        passwordValidatorManager.register(lowerCaseValidator);

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @ParameterizedTest(
            name = "[{index}] password={0}, minLength={1}, " +
                    "maxLength={2}, numOfSpecialChars={3}, numOfLowerCase={4}, " +
                    "numOfUpperCase={5}, numOfDigit={6}, expected={7}"
    )
    @DisplayName("Should Validate Complex Password")
    @MethodSource(value = "ComplexValidationData")
    public void checkFor_LowerCase_UpperCase_Digits_SpecialCharacters_MinLength_and_MaxLength(
            String password, int minLength, int maxLength,
            int numOfSpecialChars, int numOfLowerCase,
            int numOfUpperCase, int numOfDigit, boolean expected
    ) {

        var digitValidator = new DigitValidator(numOfDigit);
        var minLengthValidator = new MinLengthValidator(minLength);
        var maxLengthValidator = new MaxLengthValidator(maxLength);
        var upperCaseValidator = new UpperCaseValidator(numOfUpperCase);
        var lowerCaseValidator = new LowerCaseValidator(numOfLowerCase);
        var specialCharsValidator = new SpecialCharacterValidator(numOfSpecialChars);

        passwordValidatorManager.register(
                minLengthValidator, maxLengthValidator,
                upperCaseValidator, lowerCaseValidator,
                specialCharsValidator, digitValidator
        );

        var actual = passwordValidatorManager.validate(password);

        assertThat(actual.isValid()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should Not Accept Negative Values")
    public void rejectNegativeValues() {

        assertThatExceptionOfType(NegativeValidatorValueException.class)
                .isThrownBy(() -> passwordValidatorManager
                        .register(new MinLengthValidator(-1))
                );
    }

    @ParameterizedTest(
            name = "[{index}] maxLength={0}, numOfSpecialChars={1}, " +
                    "numOfLowerCase={2}, numOfUpperCase={3}, numOfDigit={4}"
    )
    @DisplayName("Should Validate MaxLength Password Conflict")
    @MethodSource(value = "MaxLengthConflictValidationData")
    public void validatePasswordRulesAgainstTheMaxLength(
            int maxLength, int numOfSpecialChars,
            int numOfLowerCase, int numOfUpperCase, int numOfDigit
    ) {

        var digitValidator = new DigitValidator(numOfDigit);
        var maxLengthValidator = new MaxLengthValidator(maxLength);
        var upperCaseValidator = new UpperCaseValidator(numOfUpperCase);
        var lowerCaseValidator = new LowerCaseValidator(numOfLowerCase);
        var specialCharsValidator = new SpecialCharacterValidator(numOfSpecialChars);

        assertThatExceptionOfType(PasswordValidationConflictException.class)
                .isThrownBy(() ->
                        passwordValidatorManager.register(
                                digitValidator,
                                maxLengthValidator,
                                upperCaseValidator,
                                lowerCaseValidator,
                                specialCharsValidator
                        )
                );

    }
}
