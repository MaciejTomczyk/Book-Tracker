package com.mt.booktracker;


import org.apache.commons.validator.routines.ISBNValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    ISBNValidator ISBNValidator() {
        return new ISBNValidator();
    }
}
