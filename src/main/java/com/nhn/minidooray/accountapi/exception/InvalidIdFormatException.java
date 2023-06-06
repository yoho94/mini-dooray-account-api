package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidIdFormatException extends ApiException{
    public InvalidIdFormatException() {
        super("ID format is invalid", HttpStatus.BAD_REQUEST);
    }
}
