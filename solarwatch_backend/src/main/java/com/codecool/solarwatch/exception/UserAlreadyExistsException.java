package com.codecool.solarwatch.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("User already exists, please choose another username!");
    }
}
