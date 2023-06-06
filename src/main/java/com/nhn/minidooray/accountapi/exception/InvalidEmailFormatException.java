package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailFormatException extends ApiException{
public InvalidEmailFormatException() {
    super("Email format is invalid", HttpStatus.BAD_REQUEST);
}
}
