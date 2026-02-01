package com.booking.exceptions;

public class ScreenNotFoundException extends RuntimeException{
    public ScreenNotFoundException(String message) {
        super(message);
    }
}
