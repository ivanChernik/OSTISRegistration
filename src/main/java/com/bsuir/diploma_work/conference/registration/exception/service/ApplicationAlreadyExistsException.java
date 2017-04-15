package com.bsuir.diploma_work.conference.registration.exception.service;


public class ApplicationAlreadyExistsException extends RuntimeException {
    public ApplicationAlreadyExistsException() {
        super();
    }

    public ApplicationAlreadyExistsException(String message) {
        super(message);
    }

    public ApplicationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
