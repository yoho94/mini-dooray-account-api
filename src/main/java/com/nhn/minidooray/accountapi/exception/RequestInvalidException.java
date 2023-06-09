package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class RequestInvalidException extends ApiException {
    private static final String SUFFIX = " is request fail";

    public RequestInvalidException(String target) {
        super( target+SUFFIX, HttpStatus.BAD_REQUEST.value());
    }
}
