package com.nhn.minidooray.accountapi.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.minidooray.accountapi.config.RequestMappingProperties.Account;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.service.AccountService;
import lombok.val;
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


    @Test
    void createAccount_ValidRequest_ReturnsCreated() throws Exception {
        AccountCreateRequest request = AccountCreateRequest.builder()
            .id( "normalId" )
            .name( "normalName" )
            .email( "normalEmail@email.com" )
            .password( "normalPassword" )
            .build();

        mockMvc.perform( post( "/account-api/accounts" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( status().isCreated() )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.CREATED.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "created successfully" ) );
    }

    @Test
    void createAccount_InvalidRequest_ThrowsValidationFailedException() throws Exception {
        AccountCreateRequest request = AccountCreateRequest.builder()
            .id( "id" )
            .name( "normalName" )
            .email( "normalEmail@email.com" )
            .password( "normalPassword" )
            .build();

        mockMvc.perform( post( "/account-api/accounts" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.BAD_REQUEST.value() ) );
    }

    @Test
    void updateAccountName_ValidRequest_ReturnsUpdated() throws Exception {
        // Arrange
        String id = "normalId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( "updatedName" )
            .build();

        mockMvc.perform( put( "/account-api/accounts/update/" + id + "/name" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "updated successfully" ) );
    }

    @Test
    void updateAccountName_InvalidRequest_ThrowsValidationFailedException() throws Exception {
        // TODO NAME이 null값이 들어가도 200 OK 발생 중
        String id = "normalId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( null )
            .build();

        mockMvc.perform( put( "/account-api/accounts/update/" + id + "/name" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.BAD_REQUEST.value() ) );
    }

    @Test
    void updateAccountPassword_ValidRequest_ReturnsUpdated() throws Exception {
        String id = "normalId";

        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .password( "updatedPassword" )
            .build();


        mockMvc.perform( put( "/account-api/accounts/update/" + id + "/password" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "updated successfully" ) );
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