package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class DataIntergrityViolationExceptionTest {

    @Test
    void testDataIntergrityViolationException() {
        String target = "testData";

        DataIntergrityViolationException exception = new DataIntergrityViolationException( target );

        assertEquals( target + " Intergrity violation", exception.getMessage() );
        assertEquals( HttpStatus.BAD_REQUEST.value(), exception.getStatusCode() );
    }
}
