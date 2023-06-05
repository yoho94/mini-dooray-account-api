package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super("error." + message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}