package com.testtask.ClearSolution.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserIsNotOldEnoughException extends RuntimeException{
    public UserIsNotOldEnoughException() {
        super("user is not old enough, creation is not allowed.");
    }
}
