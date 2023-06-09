package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReferencedColumnException extends ApiException {

    private static final String SUFFIX = " don't match the referenced column";

    public ReferencedColumnException(String target) {
        super( target+SUFFIX, HttpStatus.BAD_REQUEST.value());
    }

}
