package com.bsuir.diploma_work.conference.registration.exception.service;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(value = INTERNAL_SERVER_ERROR)
public class EmailWasNotSendException extends RuntimeException {
    public EmailWasNotSendException() {
        super();
    }

    public EmailWasNotSendException(String message) {
        super(message);

    }

    public EmailWasNotSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
