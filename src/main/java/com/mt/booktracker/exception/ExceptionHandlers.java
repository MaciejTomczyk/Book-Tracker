package com.mt.booktracker.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(value = BookNotFoundException.class)
    public ResponseEntity<Void> handleBookNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = BookAlreadyExistsException.class)
    public ResponseEntity<Void> handleBookExistsException() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleDataIntegrityViolation() {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}
