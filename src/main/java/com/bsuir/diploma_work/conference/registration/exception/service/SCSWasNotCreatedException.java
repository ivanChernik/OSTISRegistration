package com.bsuir.diploma_work.conference.registration.exception.service;


import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(value = INTERNAL_SERVER_ERROR)
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
