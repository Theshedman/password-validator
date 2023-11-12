package com.shedrack.validator;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

abstract class PasswordValidatorTestData {

    protected static Stream<Arguments> MinLengthValidatorData() {

        // password, minLength, expected
        return Stream.of(
                Arguments.of("a", 3, false),
                Arguments.of("abc", 3, true),
                Arguments.of("love", 4, true),
                Arguments.arguments("pa", 5, false)
        );
    }

    protected static Stream<Arguments> MaxLengthValidatorData() {

        // password, maxLength, expected
        return Stream.of(
                Arguments.of("password", 15, true),
                Arguments.of("superMario", 8, false),
                Arguments.of("designer_dau", 6, false),
                Arguments.arguments("seurityMax", 12, true)
        );
    }

    protected static Stream<Arguments> ConflictValidationData() {

        // minLength, maxLength
        return Stream.of(
                Arguments.of(16, 8),
                Arguments.of(5, 3),
                Arguments.of(11, 1),
                Arguments.of(4, 3),
                Arguments.of(5, 4),
                Arguments.of(8, 7)
        );
    }

    protected static Stream<Arguments> DigitValidationData() {

        // password, numberOfDigits, expected
        return Stream.of(
                Arguments.of("abc", 1, false),
                Arguments.of("abc3", 1, true),
                Arguments.of("a2b14", 2, true),
                Arguments.of("90adt1abc", 4, false)
        );
    }

    protected static Stream<Arguments> DigitMinAndMaxLengthValidationData() {

        // password, minLength, maxLength, numberOfDigits, expected
        return Stream.of(
                Arguments.of("abc", 4, 6, 1, false),
                Arguments.of("a1bcpa0ss", 4, 6, 1, false),
                Arguments.of("apa0ss", 4, 6, 1, true),
                Arguments.of("ab3lsk63cup1", 2, 16, 4, true),
                Arguments.of("1b9", 3, 5, 2, true),
                Arguments.of("1bath", 3, 5, 2, false)
        );
    }

    protected static Stream<Arguments> SpecialCharacterValidationData() {

        // password, numberOfSpecialCharacters, expected
        return Stream.of(
                Arguments.of("ablac", 1, false),
                Arguments.of("$a1bcpa0ss", 2, false),
                Arguments.of("apa#0ss%", 2, true)
        );
    }

    protected static Stream<Arguments> UpperCaseValidationData() {

        // password, numberOfUpperCase, expected
        return Stream.of(
                Arguments.of("ablac", 1, false),
                Arguments.of("$a1bCpa0ss", 1, true),
                Arguments.of("Ma1bCpA0SsT", 5, true),
                Arguments.of("Ma1bCpsT", 5, false),
                Arguments.of("Ma1bCpsT", 2, true)
        );
    }

    protected static Stream<Arguments> LowerCaseValidationData() {

        // password, numberOfLowerCase, expected
        return Stream.of(
                Arguments.of("aBL3@", 1, true),
                Arguments.of("AJAH", 1, false),
                Arguments.of("BLESSdMAef-lDn", 5, true),
                Arguments.of("Ma1tCNNT", 5, false),
                Arguments.of("Ma1bCPsT", 2, true)
        );
    }

    protected static Stream<Arguments> ComplexValidationData() {

        // password, minLength, maxLength, specialChars, lowerCase, upperCase, digits, expected
        return Stream.of(
                Arguments.of("dI$", 5, 6, 1, 3, 1, 1, false),
                Arguments.of("0amdI$", 3, 6, 1, 3, 1, 1, true),
                Arguments.of("0amdI$B", 3, 7, 1, 3, 2, 1, true),
                Arguments.of("0am3I$", 3, 6, 1, 3, 1, 1, false),
                Arguments.of("0mdI$", 3, 8, 1, 3, 1, 1, false),
                Arguments.of("02mdI$B", 3, 7, 1, 3, 2, 1, false)
        );
    }

    protected static Stream<Arguments> MaxLengthConflictValidationData() {

        // maxLength, specialChars, lowerCase, upperCase, digits
        return Stream.of(
                Arguments.of(6, 1, 3, 2, 1),
                Arguments.of(4, 1, 2, 1, 1),
                Arguments.of(7, 2, 3, 2, 1),
                Arguments.of(10, 4, 1, 4, 6),
                Arguments.of(8, 3, 1, 4, 1)
        );
    }
}
