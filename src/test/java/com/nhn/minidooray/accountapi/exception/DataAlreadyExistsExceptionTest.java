package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class DataAlreadyExistsExceptionTest {

    @Test
    void testDataAlreadyExistsException() {
        String target = "testData";

        DataAlreadyExistsException exception = new DataAlreadyExistsException( target );

        assertEquals( target + " already exists", exception.getMessage() );
        assertEquals( HttpStatus.CONFLICT.value(), exception.getStatusCode() );
    }
}
