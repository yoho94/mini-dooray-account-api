package com.nhn.minidooray.accountapi.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class IdOrEmailUtillsTest {

    @Test
    void testCheckIdOrEmailWithEmail() {
        String email = "test@example.com";
        boolean result = IdOrEmailUtills.checkIdOrEmail( email );
        assertTrue( result );
    }

    @Test
    void testCheckIdOrEmailWithId() {
        String id = "testUser123";
        boolean result = IdOrEmailUtills.checkIdOrEmail( id );
        assertFalse( result );
    }

    @Test
    void testCheckIdWithEmail() {
        String email = "test@example.com";
        boolean result = IdOrEmailUtills.checkId( email );
        assertFalse( result );
    }

    @Test
    void testCheckIdWithId() {
        String id = "testUser123";
        boolean result = IdOrEmailUtills.checkId( id );
        assertTrue( result );
    }

    @Test
    void testCheckEmailWithEmail() {
        String email = "test@example.com";
        boolean result = IdOrEmailUtills.checkEmail( email );
        assertTrue( result );
    }

    @Test
    void testCheckEmailWithId() {
        String id = "testUser123";
        boolean result = IdOrEmailUtills.checkEmail( id );
        assertFalse( result );
    }
}