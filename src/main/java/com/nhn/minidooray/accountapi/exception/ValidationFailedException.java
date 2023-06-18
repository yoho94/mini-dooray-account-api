package com.nhn.minidooray.accountapi.exception;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public class ValidationFailedException extends ApiException {


    public ValidationFailedException(BindingResult bindingResult) {
        super( bindingResult.getFieldErrors().stream()
                .map( f -> f.getField() + " : " + f.getDefaultMessage() )
                .collect( Collectors.joining( "," ) ),
            HttpStatus.BAD_REQUEST.value() );
    }
}