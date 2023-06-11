package com.nhn.minidooray.accountapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountApiController.class)
class AccountApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private ApiMessageProperties apiMessageProperties;

    @Test
    void createAccount_ValidRequest_ReturnsCreated() throws Exception {
        // Arrange
        AccountCreateRequest request = AccountCreateRequest.builder()
            .id("normalId")
            .name("normalName")
            .email("normalEmail@email.com")
            .password("normalPassword")
            .build();
        when(apiMessageProperties.getCreateSuccMessage()).thenReturn("Account created successfully.");

        mockMvc.perform(post("/account-api/accounts")
                .contentType( MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.header.successful").value(true))
            .andExpect(jsonPath("$.header.resultCode").value( HttpStatus.CREATED.value()))
            .andExpect(jsonPath("$.header.resultMessage").value("created successfully"));
    }

    @Test
    void createAccount_InvalidRequest_ThrowsValidationFailedException() throws Exception {
        AccountCreateRequest request = AccountCreateRequest.builder()
            .id("id")
            .name("normalName")
            .email("normalEmail@email.com")
            .password("normalPassword")
            .build();

        mockMvc.perform(post("/account-api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    @Test
    void updateAccountName() {
    }

    @Test
    void updateAccountPasswordById() {
    }

    @Test
    void updateLastLoginAtById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getAccount() {
    }

    @Test
    void getAccountByEmail() {
    }
}