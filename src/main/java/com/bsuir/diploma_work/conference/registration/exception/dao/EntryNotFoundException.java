package com.bsuir.diploma_work.conference.registration.exception.dao;


public class EntryNotFoundException extends RuntimeException{
    public EntryNotFoundException() {
        super();
    }

    public EntryNotFoundException(String message) {
        super(message);
    }

    public EntryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
