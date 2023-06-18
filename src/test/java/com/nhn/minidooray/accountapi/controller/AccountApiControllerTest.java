package com.nhn.minidooray.accountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        String id = "nornalId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( "sdfhishivxcjhihweuhfiuqoeirywiuhvsjvbdsfsdfihxcvhihvuiwf" )
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
    void updateLastLoginAtById() throws Exception {
        String id = "normalId";

        mockMvc.perform( get( "/account-api/login/" + id ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "updated successfully" ) );
    }


    @Test
    void getAllAccounts_ValidRequest_ReturnsAccountList() throws Exception {
        List<AccountResponse> accountList = new ArrayList<>();
        accountList.add( AccountResponse.builder()
            .id( "normalId1" )
            .name( "normalName1" )
            .email( "normalEmail1@email.com" )
            .password( "normalPassword1" )
            .accountStateCode( AccountStateType.REGISTER.getCode() )
            .accountStateChangeAt( LocalDateTime.now() )
            .lastLoginAt( LocalDateTime.now() )
            .createAt( LocalDateTime.now() )
            .build() );
        accountList.add( AccountResponse.builder()
            .id( "normalId2" )
            .name( "normalName2" )
            .email( "normalEmail2@email.com" )
            .password( "normalPassword2" )
            .accountStateCode( AccountStateType.REGISTER.getCode() )
            .accountStateChangeAt( LocalDateTime.now() )
            .lastLoginAt( LocalDateTime.now() )
            .createAt( LocalDateTime.now() )
            .build() );
        Page<AccountResponse> page = new PageImpl<>( accountList );
        when( accountService.getAll( any( Pageable.class ) ) ).thenReturn( page );

        mockMvc.perform( get( "/account-api/accounts" )
                .param( "page", "0" )
                .param( "size", "10" )
                .param( "sort", "id,asc" ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "retrieved successfully" ) )
            .andExpect( jsonPath( "$.result" ).isArray() )
            .andExpect( jsonPath( "$.result[0].content[0].id" ).value( accountList.get( 0 ).getId() ) )
            .andExpect( jsonPath( "$.result[0].content[0].name" ).value( accountList.get( 0 ).getName() ) );
    }


    @Test
    void getAccount() throws Exception {
        AccountResponse response = AccountResponse.builder()
            .id( "normalId1" )
            .name( "normalName1" )
            .email( "normalEmail1@email.com" )
            .password( "normalPassword1" )
            .accountStateCode( AccountStateType.REGISTER.getCode() )
            .accountStateChangeAt( LocalDateTime.now() )
            .lastLoginAt( LocalDateTime.now() )
            .createAt( LocalDateTime.now() )
            .build();

        when( accountService.get( "normalId1" ) ).thenReturn( response );

        mockMvc.perform( get( "/account-api/accounts/" + "normalId1" ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "retrieved successfully" ) )
            .andExpect( jsonPath( "$.result[0].id" ).value( response.getId() ) )
            .andExpect( jsonPath( "$.result[0].name" ).value( response.getName() ) );
    }

    @Test
    void getAccountByEmail() throws Exception {
        AccountResponse response = AccountResponse.builder()
            .id( "normalId1" )
            .name( "normalName1" )
            .email( "normalEmail1@email.com" )
            .password( "normalPassword1" )
            .accountStateCode( AccountStateType.REGISTER.getCode() )
            .accountStateChangeAt( LocalDateTime.now() )
            .lastLoginAt( LocalDateTime.now() )
            .createAt( LocalDateTime.now() )
            .build();

        when( accountService.findByEmail( "normalId1@email.com" ) ).thenReturn( response );

        mockMvc.perform( get( "/account-api/accounts/email/" + "normalId1@email.com" ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value( "retrieved successfully" ) )
            .andExpect( jsonPath( "$.result[0].email" ).value( response.getEmail() ) )
            .andExpect( jsonPath( "$.result[0].name" ).value( response.getName() ) );
    }

}