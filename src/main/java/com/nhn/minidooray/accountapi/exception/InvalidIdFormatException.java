package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidIdFormatException extends ApiException {

    private static final String SUFFIX = " is format invalid";

    public InvalidIdFormatException(String target) {
        super( target + SUFFIX, HttpStatus.BAD_REQUEST.value() );
    }
}
