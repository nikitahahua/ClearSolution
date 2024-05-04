package com.testtask.ClearSolution.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidPropertiesException extends RuntimeException{
    public InvalidPropertiesException(String message) {
        super(message);
    }
}
