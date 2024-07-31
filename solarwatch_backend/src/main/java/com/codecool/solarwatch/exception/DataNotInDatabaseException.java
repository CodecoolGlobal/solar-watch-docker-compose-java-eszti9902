package com.codecool.solarwatch.exception;

public class DataNotInDatabaseException extends RuntimeException {
    public DataNotInDatabaseException() {
        super("Data not in database yet, you need to be an admin to add a city!");
    }
}
