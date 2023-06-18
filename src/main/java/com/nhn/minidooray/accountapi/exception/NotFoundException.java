package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends ApiException {

    private static final String SUFFIX = " not found";

    public NotFoundException(String target) {
        super( target + SUFFIX, HttpStatus.NOT_FOUND.value() );
    }

}
