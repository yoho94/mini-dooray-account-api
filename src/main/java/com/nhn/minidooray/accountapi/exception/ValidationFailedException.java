package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ValidationFailedException extends ApiException {

    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}