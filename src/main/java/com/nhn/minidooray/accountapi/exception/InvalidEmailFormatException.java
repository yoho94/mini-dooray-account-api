package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailFormatException extends ApiException {
    private static final String SUFFIX = " is format invalid";

    public InvalidEmailFormatException(String target) {
        super( target+SUFFIX, HttpStatus.BAD_REQUEST.value());
    }
}
