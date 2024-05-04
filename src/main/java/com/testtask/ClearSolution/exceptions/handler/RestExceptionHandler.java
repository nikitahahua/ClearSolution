package com.testtask.ClearSolution.exceptions.handler;

import com.testtask.ClearSolution.exceptions.InvalidPropertiesException;
import com.testtask.ClearSolution.exceptions.UserAlreadyExistsException;
import com.testtask.ClearSolution.exceptions.UserIsNotOldEnoughException;
import com.testtask.ClearSolution.exceptions.UserWasntFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(InvalidPropertiesException.class)
    public ErrorResponse handleInvalidPropertiesException(InvalidPropertiesException exception){
        return new ErrorResponseImpl(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        return new ErrorResponseImpl(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(UserIsNotOldEnoughException.class)
    public ErrorResponse handleUserIsNotOldEnoughException(UserIsNotOldEnoughException exception){
        return new ErrorResponseImpl(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(UserWasntFoundException.class)
    public ErrorResponse handleUserWasntFoundException(UserWasntFoundException exception){
        return new ErrorResponseImpl(HttpStatus.NOT_FOUND, exception.getMessage());
    }
}
