package com.nhn.minidooray.accountapi.exception;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.springframework.http.HttpStatus;

public class InvalidEmailFormatException extends ApiException {
    private static final String SUFFIX = " is format invalid";

    public InvalidEmailFormatException(String target) {
        super( target+SUFFIX, HttpStatus.BAD_REQUEST.value());
    }
}
