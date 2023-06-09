package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataAlreadyExistsException extends ApiException {

    private static final String SUFFIX = " already exists";

    public DataAlreadyExistsException(String target) {
        super(target + SUFFIX, HttpStatus.CONFLICT.value());
    }

}
