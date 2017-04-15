package com.bsuir.diploma_work.conference.registration.exception.service;


public class SCSWasNotCreatedException extends RuntimeException {
    public SCSWasNotCreatedException() {
        super();
    }

    public SCSWasNotCreatedException(String message) {
        super(message);
    }

    public SCSWasNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
