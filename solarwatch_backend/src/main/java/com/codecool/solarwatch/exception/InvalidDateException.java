package com.codecool.solarwatch.exception;

public class InvalidDateException extends Exception{
    public InvalidDateException(){
        super("Invalid Date, please try again");
    }
}
