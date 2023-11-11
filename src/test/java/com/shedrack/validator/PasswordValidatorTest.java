package com.shedrack.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class PasswordValidatorTest {
    private ValidatorManager passwordValidatorManager;

    public static Stream<Arguments> MinLengthValidatorData() {
        return Stream.of(
                Arguments.of(
                        new Password("a"), 3,
                        new ValidationResult(false, List.of("length must not be less than 3 characters"))
                ),
                Arguments.of(
                        new Password("abc"), 3,
                        new ValidationResult(true, List.of())
                ),
                Arguments.of(
                        new Password("love"), 4,
                        new ValidationResult(true, List.of())
                ),
                Arguments.arguments(
                        new Password("pa"), 5,
                        new ValidationResult(false, List.of("length must not be less than 5 characters"))

                )
        );
    }

    public static Stream<Arguments> MaxLengthValidatorData() {
        return Stream.of(
                Arguments.of(
                        new Password("password"), 15,
                        new ValidationResult(true, List.of())
                ),
                Arguments.of(
                        new Password("superMario"), 8,
                        new ValidationResult(false, List.of("length must not be greater than 8 characters"))
                ),
                Arguments.of(
                        new Password("designer_dau"), 6,
                        new ValidationResult(false, List.of("length must not be greater than 6 characters"))
                ),
                Arguments.arguments(
                        new Password("seurityMax"), 12,
                        new ValidationResult(true, List.of())

                )
        );
    }

    public static Stream<Arguments> ConflictValidationData() {
        return Stream.of(
                Arguments.of(16, 8),
                Arguments.of(5, 3),
                Arguments.of(11, 1),
                Arguments.of(4, 3),
                Arguments.of(5, 4),
                Arguments.of(8, 7)
        );
    }

    public static Stream<Arguments> DigitValidationData() {
        return Stream.of(
                Arguments.of(
                        new Password("abc"), 1,
                        new ValidationResult(Boolean.FALSE, List.of("must contain at least 1 digit(s)"))
                ),
                Arguments.of(
                        new Password("abc3"), 1,
                        new ValidationResult(Boolean.TRUE, null)
                ),
                Arguments.of(
                        new Password("a2b14"), 2,
                        new ValidationResult(Boolean.TRUE, null)
                ),
                Arguments.of(
                        new Password("90adt1abc"), 4,
                        new ValidationResult(Boolean.FALSE, List.of("must contain at least 4 digit(s)"))
                )
        );
    }

    public static Stream<Arguments> DigitMinAndMaxLengthValidationData() {
        return Stream.of(
                Arguments.of(
                        new Password("abc"), 4, 6, 1,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("a1bcpa0ss"), 4, 6, 1,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("apa0ss"), 4, 6, 1,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("ab3lsk63cup1"), 2, 16, 4,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("1b9"), 3, 5, 2,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("1bath"), 3, 5, 2,
                        new ValidationResult(Boolean.FALSE, List.of())
                )
        );
    }

    public static Stream<Arguments> SpecialCharacterValidationData() {
        return Stream.of(
                Arguments.of(
                        new Password("ablac"), 1,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("$a1bcpa0ss"), 2,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("apa#0ss%"), 2,
                        new ValidationResult(Boolean.TRUE, List.of())
                )
        );
    }

    public static Stream<Arguments> UpperCaseValidationData() {
        return Stream.of(
                Arguments.of(
                        new Password("ablac"), 1,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("$a1bCpa0ss"), 1,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("Ma1bCpA0SsT"), 5,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("Ma1bCpsT"), 5,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("Ma1bCpsT"), 2,
                        new ValidationResult(Boolean.TRUE, List.of())
                )
        );
    }

    public static Stream<Arguments> LowerCaseValidationData() {
        return Stream.of(
                Arguments.of(
                        new Password("aBL3@"), 1,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("AJAH"), 1,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("BLESSdMAef-lDn"), 5,
                        new ValidationResult(Boolean.TRUE, List.of())
                ),
                Arguments.of(
                        new Password("Ma1tCNNT"), 5,
                        new ValidationResult(Boolean.FALSE, List.of())
                ),
                Arguments.of(
                        new Password("Ma1bCPsT"), 2,
                        new ValidationResult(Boolean.TRUE, List.of())
                )
        );
    }

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
