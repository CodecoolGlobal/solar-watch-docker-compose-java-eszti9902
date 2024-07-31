package com.codecool.solarwatch.exception;

public class WrongUrlException extends Exception{
    public WrongUrlException(){
        super("City probably misspelled");
    }
}
