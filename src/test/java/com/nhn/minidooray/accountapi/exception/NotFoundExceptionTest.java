package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class NotFoundExceptionTest {

    @Test
    void testNotFoundException() {
        String target = "testData";

        NotFoundException exception = new NotFoundException( target );

        assertEquals( target + " not found", exception.getMessage() );
        assertEquals( HttpStatus.NOT_FOUND.value(), exception.getStatusCode() );
    }
}
