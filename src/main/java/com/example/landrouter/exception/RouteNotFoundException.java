package com.example.landrouter.exception;

public class RouteNotFoundException extends RuntimeException{

    public RouteNotFoundException(String message) {
        super(message);
    }
}
