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
    public void minimumLengthValidation(Password password, int minLength, ValidationResult expected) {
        PasswordValidator minLengthValidator = new MinLengthValidator(minLength);
        passwordValidatorManager.register(minLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] password={0}, maxLength={1}, expected={2}")
    @MethodSource(value = "MaxLengthValidatorData")
    @DisplayName("Should Not Be Greater Than Max Length")
    public void maximumLengthValidation(Password password, int maxLength, ValidationResult expected) {
        PasswordValidator maxLengthValidator = new MaxLengthValidator(maxLength);
        passwordValidatorManager.register(maxLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] MinLength={0}, MaxLength={1}")
    @DisplayName("Should Not Conflict With Another Validator")
    @MethodSource(value = "ConflictValidationData")
    public void checkForConflict(int min, int max) {
        var minValidator = new MinLengthValidator(min);
        var maxValidator = new MaxLengthValidator(max);

        assertThatExceptionOfType(PasswordValidationConflictException.class)
                .isThrownBy(() -> passwordValidatorManager.register(minValidator, maxValidator));

    }

    @ParameterizedTest(name = "[{index}] password={0}, digitCount={1}, expected={2}")
    @DisplayName("Should Contain Digits")
    @MethodSource(value = "DigitValidationData")
    public void checkForDigits(Password password, int digitCount, ValidationResult expected) {
        DigitValidator digitValidator = new DigitValidator(digitCount);
        passwordValidatorManager.register(digitValidator);

        var actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] password={0}, minLength={1}, maxLength={2}, digitCount={3}, expected={4}")
    @DisplayName("Should Validate Digits, Min And MaxLength")
    @MethodSource(value = "DigitMinAndMaxLengthValidationData")
    public void validateDigitMinAndMaxLength(
            Password password, int minLength, int maxLength,
            int digitCount, ValidationResult expected
    ) {
        var digitValidator = new DigitValidator(digitCount);
        var minLengthValidator = new MinLengthValidator(minLength);
        var maxLengthValidator = new MaxLengthValidator(maxLength);

        passwordValidatorManager.register(digitValidator, minLengthValidator, maxLengthValidator);

        var actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] password={0}, numSpecialChars={1}, expected={2}")
    @DisplayName("Should Contain Special Characters")
    @MethodSource(value = "SpecialCharacterValidationData")
    public void checkForSpecialCharacters(
            Password password, int numSpecialChars, ValidationResult expected
    ) {
        var specialCharacterValidator = new SpecialCharacterValidator(numSpecialChars);
        passwordValidatorManager.register(specialCharacterValidator);

        var actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] password={0}, upperCaseCount={1}, expected={2}")
    @DisplayName("Should Contain Uppercase letters")
    @MethodSource(value = "UpperCaseValidationData")
    public void checkForUpperCaseCharacters(
            Password password, int upperCaseCount, ValidationResult expected
    ) {
        var upperCaseValidator = new UpperCaseValidator(upperCaseCount);
        passwordValidatorManager.register(upperCaseValidator);

        var actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }

    @ParameterizedTest(name = "[{index}] password={0}, lowerCaseCount={1}, expected={2}")
    @DisplayName("Should Contain Lowercase letters")
    @MethodSource(value = "LowerCaseValidationData")
    public void checkForLowerCaseCharacters(
            Password password, int lowerCaseCount, ValidationResult expected
    ) {
        var lowerCaseValidator = new LowerCaseValidator(lowerCaseCount);
        passwordValidatorManager.register(lowerCaseValidator);

        var actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
    }
}
