package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.exception.DataNotInDatabaseException;
import com.codecool.solarwatch.exception.InvalidDateException;
import com.codecool.solarwatch.exception.UserAlreadyExistsException;
import com.codecool.solarwatch.exception.WrongUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SolarWatchControllerAdvice {
    @ResponseBody
    @ExceptionHandler(WrongUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String wrongUrlException(Exception e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidDateExceptionHandler(InvalidDateException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(DataNotInDatabaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dataNotInDatabaseExceptionHandler(DataNotInDatabaseException e) {return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String userAlreadyExistsExceptionHandler(UserAlreadyExistsException e) {return e.getMessage();}
}
