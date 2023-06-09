package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataIntergrityViolationException extends ApiException {

    private static final String SUFFIX = " Intergrity violation";

    public DataIntergrityViolationException(String target) {
        super( target + SUFFIX, HttpStatus.BAD_REQUEST.value() );
    }
}
