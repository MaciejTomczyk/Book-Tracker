package com.mt.booktracker.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
@SuppressWarnings("unused")
public @interface ValidIsbn {
    String message() default "Invalid isbn";

    String[] allowedFormats() default {IsbnConfiguration.ISBN10, IsbnConfiguration.ISBN13};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
