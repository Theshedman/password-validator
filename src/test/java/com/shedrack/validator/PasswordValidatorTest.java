package com.shedrack.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class PasswordValidatorTest {
    private ValidatorManager passwordValidatorManager;

    public static Stream<Arguments> MinLengthValidatorData() {
        return Stream.of(
                Arguments.of(
                        new Password("a"), 3,
                        new ValidationResult(false, "length must be minimum of 3 characters")
                ),
                Arguments.of(
                        new Password("abc"), 3,
                        new ValidationResult(true, null)
                ),
                Arguments.of(
                        new Password("love"), 4,
                        new ValidationResult(true, null)
                ),
                Arguments.arguments(
                        new Password("pa"), 5,
                        new ValidationResult(false, "length must be minimum of 5 characters")

                )
        );
    }

    public static Stream<Arguments> MaxLengthValidatorData() {
        return Stream.of(
                Arguments.of(
                        new Password("password"), 15,
                        new ValidationResult(true, null)
                ),
                Arguments.of(
                        new Password("superMario"), 8,
                        new ValidationResult(false, "length must be maximum of 8 characters")
                ),
                Arguments.of(
                        new Password("designer_dau"), 6,
                        new ValidationResult(false, "length must be maximum of 6 characters")
                ),
                Arguments.arguments(
                        new Password("seurityMax"), 12,
                        new ValidationResult(true, null)

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

    @ParameterizedTest
    @MethodSource(value = "MinLengthValidatorData")
    @DisplayName("Should Not Be Less Than Min Length")
    public void minimumLengthValidation(Password password, int minLength, ValidationResult expected) {
        PasswordValidator minLengthValidator = new MinLengthValidator(minLength);
        passwordValidatorManager.register(minLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
        assertThat(actual.message()).isEqualTo(expected.message());
    }

    @ParameterizedTest
    @MethodSource(value = "MaxLengthValidatorData")
    @DisplayName("Should Not Be Greater Than Max Length")
    public void maximumLengthValidation(Password password, int maxLength, ValidationResult expected) {
        PasswordValidator maxLengthValidator = new MaxLengthValidator(maxLength);
        passwordValidatorManager.register(maxLengthValidator);

        ValidationResult actual = passwordValidatorManager.validate(password.value());

        assertThat(actual.isValid()).isEqualTo(expected.isValid());
        assertThat(actual.message()).isEqualTo(expected.message());
    }

    @ParameterizedTest
    @DisplayName("Should Not Conflict With Another Validator")
    @MethodSource(value = "ConflictValidationData")
    public void checkForConflict(int min, int max) {
        var validatorManager = new PasswordValidatorManager();

        var minValidator = new MinLengthValidator(min);
        var maxValidator = new MaxLengthValidator(max);

        assertThatExceptionOfType(PasswordValidationConflictException.class)
                .isThrownBy(() -> validatorManager.register(minValidator, maxValidator));

    }
}
