package com.nhn.minidooray.accountapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiMessagePropertiesTest {

    @Autowired
    private ApiMessageProperties properties;

    @Test
    void testGetCreateSuccMessage() {
        assertEquals( "created successfully", properties.getCreateSuccMessage() );
    }

    @Test
    void testGetUpdateSuccMessage() {
        assertEquals( "updated successfully", properties.getUpdateSuccMessage() );
    }


    @Test
    void testGetDeleteSuccMessage() {
        assertEquals( "deleted successfully", properties.getDeleteSuccMessage() );
    }

    @Test
    void testGetGetSuccMessage() {
        assertEquals( "retrieved successfully", properties.getGetSuccMessage() );
    }
}