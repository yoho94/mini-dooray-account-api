package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ApiExceptionTest {

    @Test
    void testApiExceptionWithMessageAndStatusCode() {
        String message = "Test Exception";
        int statusCode = 500;

        ApiException exception = new ApiException( message, statusCode );

        assertEquals( message, exception.getMessage() );
        assertEquals( statusCode, exception.getStatusCode() );
    }
}
