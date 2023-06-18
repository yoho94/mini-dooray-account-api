package com.nhn.minidooray.accountapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class RecentStateExceptionTest {

    @Test
    void testRecentStateException() {
        String target = "testUser";

        RecentStateException exception = new RecentStateException( target );

        assertEquals( target + " is login fail", exception.getMessage() );
        assertEquals( HttpStatus.UNAUTHORIZED.value(), exception.getStatusCode() );
    }
}
