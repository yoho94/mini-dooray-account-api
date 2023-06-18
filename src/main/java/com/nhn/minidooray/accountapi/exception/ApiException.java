package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

    private final int statusCode;

    public ApiException(String message, int statusCode) {
        super( message );
        this.statusCode = statusCode;
    }
}