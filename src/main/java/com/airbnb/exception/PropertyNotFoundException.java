package com.airbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PropertyNotFoundException extends RuntimeException{
    public PropertyNotFoundException(String message) {
        super(message);
    }
}
