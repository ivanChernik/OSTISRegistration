package com.bsuir.diploma_work.conference.registration.exception.dao;


public class EntryWasFoundException extends  RuntimeException{
    public EntryWasFoundException() {
        super();
    }

    public EntryWasFoundException(String message) {
        super(message);
    }

    public EntryWasFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
