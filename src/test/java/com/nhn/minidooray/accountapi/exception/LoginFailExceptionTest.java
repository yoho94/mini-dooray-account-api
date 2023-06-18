package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class LoginFailExceptionTest {

    @Test
    void testLoginFailException() {
        String target = "testUser";

        LoginFailException exception = new LoginFailException( target );

        assertEquals( target + " is login fail", exception.getMessage() );
        assertEquals( HttpStatus.UNAUTHORIZED.value(), exception.getStatusCode() );
    }
}