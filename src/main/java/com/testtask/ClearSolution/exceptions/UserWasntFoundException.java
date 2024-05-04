package com.testtask.ClearSolution.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserWasntFoundException extends RuntimeException{
    public UserWasntFoundException(String message) {
        super(message);
    }
}
