package com.booking.exceptions;

public class DuplicateTheatreException extends RuntimeException{
    public DuplicateTheatreException(String message) {
        super(message);
    }
}
