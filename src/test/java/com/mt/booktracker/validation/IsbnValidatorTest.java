package com.mt.booktracker.validation;

import org.apache.commons.validator.routines.ISBNValidator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class IsbnValidatorTest {

    private final IsbnValidator isbnValidator = new IsbnValidator(new ISBNValidator());

    @ParameterizedTest
    @MethodSource("isbnProvider")
    void isValid(String isbn, boolean expected) {
        //given
        isbnValidator.initialize(List.of(IsbnConfiguration.ISBN10_PATTERN,IsbnConfiguration.ISBN13_PATTERN));

        //when
        var result = isbnValidator.isValid(isbn, null);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> isbnProvider() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of("12345", false),
                Arguments.of("978-2-123-45680-3", true),
                Arguments.of("8-535-90277-5", true)
        );
    }
}