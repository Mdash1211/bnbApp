package com.airbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExists extends RuntimeException{
    public UserExists(String msg) {
        super(msg);
    }
}
