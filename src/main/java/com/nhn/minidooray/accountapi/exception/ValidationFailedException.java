package com.nhn.minidooray.accountapi.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ValidationFailedException extends ApiException {

    public ValidationFailedException(BindingResult bindingResult) {
        super(bindingResult.getAllErrors()
            .stream()
            .map(error -> new StringBuilder().append("ObjectName=").append(error.getObjectName())
                .append(",Message=").append(error.getDefaultMessage())
                .append(",code=").append(error.getCode()))
            .collect(Collectors.joining(" | ")), HttpStatus.BAD_REQUEST);
    }
}