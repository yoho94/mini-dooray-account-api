package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends ApiException {

    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getFieldErrors().stream()
                        .map(f -> f.getField() + " : " + f.getDefaultMessage())
                        .collect(Collectors.joining(",")),
                HttpStatus.BAD_REQUEST.value());
    }
}