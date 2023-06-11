package com.nhn.minidooray.accountapi.exception;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class InvalidIdFormatExceptionTest {

    @Test
    void testInvalidIdFormatException() {
        String target = "testId";

        InvalidIdFormatException exception = new InvalidIdFormatException( target );

        assertEquals( target + " is format invalid", exception.getMessage() );
        assertEquals( HttpStatus.BAD_REQUEST.value(), exception.getStatusCode() );
    }
}