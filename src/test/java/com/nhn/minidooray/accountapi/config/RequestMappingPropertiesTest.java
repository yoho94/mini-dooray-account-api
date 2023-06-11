package com.nhn.minidooray.accountapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequestMappingPropertiesTest {

    @Autowired
    private RequestMappingProperties properties;


    @Test
    void whenCreateAccountPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts", properties.getAccount().getCreateAccount() );
    }

    @Test
    void whenReadAccountListPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts", properties.getAccount().getReadAccountList() );
    }

    @Test
    void whenReadAccountByIdPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts/{id}", properties.getAccount().getReadAccountById() );
    }

    @Test
    void whenReadAccountByEmailPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts/email/{email}", properties.getAccount().getReadAccountByEmail() );
    }

    @Test
    void whenUpdateLastLoginAtPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/login/{id}", properties.getAccount().getUpdateLastLoginAt() );
    }

    @Test
    void whenUpdateAccountPropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts/update/{id}", properties.getAccount().getUpdateAccount() );
    }

    @Test
    void whenUpdateAccountNamePropertyLoaded_thenItShouldMatchExpectedValue() {
        assertEquals( "/accounts/update/{id}/name",
            properties.getAccount().getUpdateAccountName() );
    }
// TODO PROPERTY 수정 필요
//    @Test
//   void whenUpdateAccountPasswordPropertyLoaded_thenItShouldMatchExpectedValue() {
//        assertEquals("/accounts/update/{id}/password", properties.getAccount().getUpdateAccountPasswordById());
//    }
}
