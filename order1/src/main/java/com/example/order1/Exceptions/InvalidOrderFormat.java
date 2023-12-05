package com.example.order1.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidOrderFormat extends RuntimeException{
    public InvalidOrderFormat(String message) {
        super(message);
    }
}
