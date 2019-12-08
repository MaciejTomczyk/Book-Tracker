package com.mt.booktracker.validation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;

import static com.mt.booktracker.validation.IsbnConfiguration.initPatters;

@Component
@RequiredArgsConstructor
public class IsbnValidator implements ConstraintValidator<ValidIsbn, String> {

    private final ISBNValidator isbnValidator;

    private static final Map<String, String> patterns = initPatters();

    private static List<String> allowableFormats = new ArrayList<>();

    @Override
    public void initialize(ValidIsbn constraintAnnotation) {
        Arrays.stream(constraintAnnotation.allowedFormats())
                .map(patterns::get)
                .forEach(allowableFormats::add);
    }

    void initialize(List<String> formats) {
        allowableFormats = formats;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Optional.ofNullable(value)
                .map(i -> allowableFormats.stream().anyMatch(value::matches) && isbnValidator.isValid(i))
                .orElse(false);
    }
}
