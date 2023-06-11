package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class InvalidRequestExceptionTest {

    @Test
    void testInvalidRequestException() {
        String target = "testRequest";

        InvalidRequestException exception = new InvalidRequestException( target );

        assertEquals( target + " is request fail", exception.getMessage() );
        assertEquals( HttpStatus.BAD_REQUEST.value(), exception.getStatusCode() );
    }
}