package com.nhn.minidooray.accountapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

@WebMvcTest(AccountAccountStateController.class)
class AccountAccountStateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountAccountStateService accountAccountStateService;

    @MockBean
    private ApiMessageProperties apiMessageProperties;

    @Test
    void createAccountAccountState_ValidRequest_ReturnsCreated() throws Exception {
        AccountAccountCreateRequest request = AccountAccountCreateRequest.builder()
            .accountId( "normalAccountId" )
            .accountStateCode( AccountStateType.DORMANT.getCode() )
            .build();
        when( apiMessageProperties.getCreateSuccMessage() ).thenReturn( "created successfully" );

        mockMvc.perform( post( "/account-api/account-account-state" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.CREATED.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value(
                "AccountAccountState created successfully" ) );
    }

    @Test
    void createAccountAccountState_InvalidRequest_ThrowsValidationFailedException()
        throws Exception {
        AccountAccountCreateRequest request = AccountAccountCreateRequest.builder()
            .accountId( "invalidId" )
            .accountStateCode( AccountStateType.DORMANT.getCode() )
            .build();
        when( apiMessageProperties.getCreateSuccMessage() ).thenReturn( "created successfully" );

        mockMvc.perform( post( "/account-api/account-account-state" )
                .contentType( MediaType.APPLICATION_JSON )
                .content( new ObjectMapper().writeValueAsString( request ) ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.CREATED.value() ) );
    }

    @Test
    void getAccountWithStateByAccount_ValidRequest_ReturnsAccountStateList() throws Exception {
        List<CommonAccountWithStateResponse> accountList = new ArrayList<>();
        accountList.add( CommonAccountWithStateResponse.builder()
            .accountId( "testId1" )
            .stateCode( AccountStateType.ACTIVE.getCode() )
            .changeAt( LocalDateTime.now() )
            .build() );
        accountList.add( CommonAccountWithStateResponse.builder()
            .accountId( "testId2" )
            .stateCode( AccountStateType.DORMANT.getCode() )
            .changeAt( LocalDateTime.now() )
            .build() );
        Page<CommonAccountWithStateResponse> page = new PageImpl<>( accountList );

        when( accountAccountStateService.getByAccount( anyString(),
            any( Pageable.class ) ) ).thenReturn( page );
        when( apiMessageProperties.getGetSuccMessage() ).thenReturn( "success" );
        mockMvc.perform(
                get( "/account-api/account-account-state/" + "testId1" + "/list", "testId1" )
                    .param( "page", "0" )
                    .param( "size", "10" )
                    .param( "sort", "accountId,asc" ) )
            .andExpect( jsonPath( "$.header.successful" ).value( true ) )
            .andExpect( jsonPath( "$.header.resultCode" ).value( HttpStatus.OK.value() ) )
            .andExpect( jsonPath( "$.header.resultMessage" ).value(
                "All account-account-states by account ID success" ) )
            .andExpect( jsonPath( "$.result[0].content" ).isArray() )
            .andExpect( jsonPath( "$.result[0].content[0].accountId" ).value(
                accountList.get( 0 ).getAccountId() ) )
            .andExpect( jsonPath( "$.result[0].content[0].stateCode" ).value(
                accountList.get( 0 ).getStateCode() ) );
    }


}