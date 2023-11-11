package com.shedrack.validator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

abstract class PasswordValidatorTestData {
    static Stream<Arguments> MinLengthValidatorData() {
        return Stream.of(
                Arguments.of("a", 3, false),
                Arguments.of("abc", 3, true),
                Arguments.of("love", 4, true),
                Arguments.arguments("pa", 5, false)
        );
    }

    static Stream<Arguments> MaxLengthValidatorData() {
        return Stream.of(
                Arguments.of("password", 15, true),
                Arguments.of("superMario", 8, false),
                Arguments.of("designer_dau", 6, false),
                Arguments.arguments("seurityMax", 12, true)
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
                Arguments.of("abc", 1, false),
                Arguments.of("abc3", 1, true),
                Arguments.of("a2b14", 2, true),
                Arguments.of("90adt1abc", 4, false)
        );
    }

    static Stream<Arguments> DigitMinAndMaxLengthValidationData() {
        return Stream.of(
                Arguments.of("abc", 4, 6, 1, false),
                Arguments.of("a1bcpa0ss", 4, 6, 1, false),
                Arguments.of("apa0ss", 4, 6, 1, true),
                Arguments.of("ab3lsk63cup1", 2, 16, 4, true),
                Arguments.of("1b9", 3, 5, 2, true),
                Arguments.of("1bath", 3, 5, 2, false)
        );
    }

    static Stream<Arguments> SpecialCharacterValidationData() {
        return Stream.of(
                Arguments.of("ablac", 1, false),
                Arguments.of("$a1bcpa0ss", 2, false),
                Arguments.of("apa#0ss%", 2, true)
        );
    }

    static Stream<Arguments> UpperCaseValidationData() {
        return Stream.of(
                Arguments.of("ablac", 1, false),
                Arguments.of("$a1bCpa0ss", 1, true),
                Arguments.of("Ma1bCpA0SsT", 5, true),
                Arguments.of("Ma1bCpsT", 5, false),
                Arguments.of("Ma1bCpsT", 2, true)
        );
    }

    static Stream<Arguments> LowerCaseValidationData() {
        return Stream.of(
                Arguments.of("aBL3@", 1, true),
                Arguments.of("AJAH", 1, false),
                Arguments.of("BLESSdMAef-lDn", 5, true),
                Arguments.of("Ma1tCNNT", 5, false),
                Arguments.of("Ma1bCPsT", 2, true)
        );
    }
}
