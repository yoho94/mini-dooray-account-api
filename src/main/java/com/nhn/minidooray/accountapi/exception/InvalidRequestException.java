package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ApiException {

    private static final String SUFFIX = " is request fail";

    public InvalidRequestException(String target) {
        super( target + SUFFIX, HttpStatus.BAD_REQUEST.value() );
    }
}
