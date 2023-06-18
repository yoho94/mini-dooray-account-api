package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class InvalidEmailFormatExceptionTest {

    @Test
    void testInvalidEmailFormatException() {
        String target = "testEmail";

        InvalidEmailFormatException exception = new InvalidEmailFormatException( target );

        assertEquals( target + " is format invalid", exception.getMessage() );
        assertEquals( HttpStatus.BAD_REQUEST.value(), exception.getStatusCode() );
    }
}