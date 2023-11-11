package com.shedrack.validator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

abstract class PasswordValidatorTestData {
    static Stream<Arguments> MinLengthValidatorData() {
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

    static Stream<Arguments> MaxLengthValidatorData() {
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

    static Stream<Arguments> ConflictValidationData() {
        return Stream.of(
                Arguments.of(16, 8),
                Arguments.of(5, 3),
                Arguments.of(11, 1),
                Arguments.of(4, 3),
                Arguments.of(5, 4),
                Arguments.of(8, 7)
        );
    }

    static Stream<Arguments> DigitValidationData() {
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

    static Stream<Arguments> DigitMinAndMaxLengthValidationData() {
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

    static Stream<Arguments> SpecialCharacterValidationData() {
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

    static Stream<Arguments> UpperCaseValidationData() {
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

    static Stream<Arguments> LowerCaseValidationData() {
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
}
