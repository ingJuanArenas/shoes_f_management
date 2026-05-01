package com.shoes_f_management.Web.Exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.shoes_f_management.Domain.Exceptions.AlreadyExistsException;
import com.shoes_f_management.Domain.Exceptions.InvalidRequestException;
import com.shoes_f_management.Domain.Exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(NotFoundException e) {
        Error error = new Error("Not Found", e.toString());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Error> handleAlreadyExistsException(AlreadyExistsException e) {
        Error error = new Error("Already Exists", e.toString());
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Error> handleInvalidRequestException(InvalidRequestException e) {
        Error error = new Error("Enrollment Operation Not Avaliable", e.toString());
        return ResponseEntity.status(400).body(error);
    }

     @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Error> handleNoResourceFoundException(NoResourceFoundException ex) {
        Error error = new Error("Not Found", ex.toString());
        return ResponseEntity.status(404).body(error);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Error> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Error error = new Error("Data Integrity Violation", ex.toString());
        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Error error = new Error("Method Argument Type Mismatch", ex.toString());
        return ResponseEntity.status(400).body(error);
    }
  
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Error error = new Error("Method Argument Not Valid", ex.toString());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception e) {
        Error error = new Error("Internal Server Error", e.toString());
        return ResponseEntity.status(500).body(error);
    }

}
