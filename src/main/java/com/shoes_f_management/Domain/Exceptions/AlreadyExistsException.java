package com.shoes_f_management.Domain.Exceptions;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException (String message){
        super(message);
    }
}
