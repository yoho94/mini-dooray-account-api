package com.nhn.minidooray.accountapi.service.impl;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhn.minidooray.accountapi.config.ValidationProperties.Account;
import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidRequestException;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class AccountServiceImplTest {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger( this.getClass() );
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountAccountStateRepository accountAccountStateRepository;
    @InjectMocks
    AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        final String[] id = {"normalId"};
        String name = "normalName";
        String password = "normalPassword";
        String email = "normalEmail@email.com";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id[0] )
            .name( name )
            .email( email )
            .password( password )
            .build();
        AccountAccountStateEntity mockAccountAccountStateEntity = AccountAccountStateEntity.builder()
            .account( mockAccountEntity )
            .pk( Pk.builder()
                .accountId( id[0] )
                .accountStateCode( AccountStateType.REGISTER.getCode() )
                .changeAt( LocalDateTime.now() )
                .build() )
            .build();


    }

    @Test
    void create() {
        String id = "normalId";
        String name = "normalName";
        String password = "normalPassword";
        String email = "normalEmail@email.com";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id )
            .name( name )
            .email( email )
            .password( password )
            .build();

        AccountCreateRequest request = AccountCreateRequest.builder()
            .id( id )
            .name( name )
            .password( password )
            .email( email )
            .build();

        AccountAccountStateEntity mockAccountAccountStateEntity = AccountAccountStateEntity.builder()
            .account( mockAccountEntity )
            .pk( Pk.builder()
                .accountId( id )
                .accountStateCode( AccountStateType.REGISTER.getCode() )
                .changeAt( LocalDateTime.now() )
                .build() )
            .build();

        when( accountRepository.save( any( AccountEntity.class ) ) ).thenReturn(
            mockAccountEntity );
        when( accountAccountStateRepository.save(
            any( AccountAccountStateEntity.class ) ) ).thenReturn( mockAccountAccountStateEntity );

        when( accountRepository.findById( id ) ).thenReturn( Optional.of( mockAccountEntity ) );

        accountService.create( request );

        AccountEntity savedAccount = accountRepository.findById( id ).get();
        assertNotNull( savedAccount );
        assertEquals( id, savedAccount.getId() );
        assertEquals( name, savedAccount.getName() );
        assertEquals( email, savedAccount.getEmail() );
    }

    @Test
    void createThrowInvalidIdFormat() {
        String id = "normalId";
        String name = "normalName";
        String password = "normalPassword";
        String email = "normalEmail@email.com";

        AccountCreateRequest invalidIdRequest = AccountCreateRequest.builder()
            .id( id + "@email.com" )
            .name( name )
            .email( email )
            .password( password )
            .build();

        assertThrows( InvalidIdFormatException.class,
            () -> accountService.create( invalidIdRequest ) );
    }

    @Test
    void createThrowInvalidEmailFormat() {
        String id = "normalId";
        String name = "normalName";
        String password = "normalPassword";
        String email = "normalEmail@email.com";

        AccountCreateRequest invalidEmailRequest = AccountCreateRequest.builder()
            .id( id )
            .name( name )
            .email( id )
            .password( password )
            .build();

        assertThrows( InvalidEmailFormatException.class,
            () -> accountService.create( invalidEmailRequest ) );
    }

    @Test
    void createThrowDataAlreadyExists() {
        String id = "normalId";
        String name = "normalName";
        String password = "normalPassword";
        String email = "normalEmail@email.com";

        AccountCreateRequest validRequest = AccountCreateRequest.builder()
            .id( id )
            .name( name )
            .email( email )
            .password( password )
            .build();

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id )
            .name( name )
            .email( email )
            .password( password )
            .build();

        when( accountRepository.save( any( AccountEntity.class ) ) ).thenReturn(
            mockAccountEntity );
        when( accountRepository.existsById( id ) ).thenReturn( false,
            true ); // 두 번째 호출시 이미 존재한다고 응답

        accountService.create( validRequest );
        assertThrows( DataAlreadyExistsException.class,
            () -> accountService.create( validRequest ) );
    }

    @Test
    void update() {
        String id = "normalId";
        String newName = "updatedName";
        String newPassword = "updatedPassword";

        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( newName )
            .password( newPassword )
            .build();

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id )
            .name( "normalName" )
            .password( "normalPassword" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.of( mockAccountEntity ) );

        ResultResponse<Void> resultResponse = accountService.update( id, request );

        assertTrue( resultResponse.getHeader().isSuccessful() );
        assertEquals( HttpStatus.OK.value(), resultResponse.getHeader().getResultCode() );
        assertEquals( newName, mockAccountEntity.getName() );
        assertEquals( newPassword, mockAccountEntity.getPassword() );
    }

    @Test
    void updateThrowsNotFoundException() {
        String id = "nonExistentId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( "updatedName" )
            .password( "updatedPassword" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.empty() );

        assertThrows( NotFoundException.class, () -> accountService.update( id, request ) );
    }

    @Test
    void updateName() {
        String id = "normalId";
        String updatedName = "updatedName";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( updatedName )
            .build();

        AccountEntity existingAccountEntity = AccountEntity.builder()
            .id( id )
            .name( "initialName" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.of( existingAccountEntity ) );

        accountService.updateName( id, request );

        assertEquals( updatedName, existingAccountEntity.getName() );
    }

    @Test
    void updateNameThrowsNotFoundException() {
        String id = "nonExistentId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .name( "updatedName" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.empty() );

        assertThrows( NotFoundException.class, () -> accountService.update( id, request ) );
    }


    @Test
    void updatePassword() {
        String id = "normalId";
        String updatedPassword = "updatedPassword";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .password( updatedPassword )
            .build();

        AccountEntity existingAccountEntity = AccountEntity.builder()
            .id( id )
            .password( "initialPassword" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.of( existingAccountEntity ) );

        accountService.updatePassword( id, request );

        assertEquals( updatedPassword, existingAccountEntity.getPassword() );

    }

    @Test
    void updatePasswordThrowsNotFoundException() {
        String id = "nonExistentId";
        AccountUpdateRequest request = AccountUpdateRequest.builder()
            .password( "updatedPassword" )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.empty() );

        assertThrows( NotFoundException.class, () -> accountService.updatePassword( id, request ) );
    }

    @Test
    void findByEmail() {
        String email = "normalEmail@email.com";
        String id = "normalId";
        String password = "normalPassword";
        String name = "normalName";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id )
            .name( name )
            .password( password )
            .email( email )
            .build();

        AccountAccountStateEntity mockAccountStateEntity = AccountAccountStateEntity.builder()
            .account( mockAccountEntity )
            .pk( Pk.builder()
                .accountId( id )
                .accountStateCode( AccountStateType.REGISTER.getCode() )
                .changeAt( LocalDateTime.now() )
                .build() )
            .build();

        when( accountRepository.findByEmail( email ) ).thenReturn(
            Optional.of( mockAccountEntity ) );
        when( accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc( id ) )
            .thenReturn( Optional.of( mockAccountStateEntity ) );

        AccountResponse result = accountService.findByEmail( email );

        assertEquals( id, result.getId() );
        assertEquals( name, result.getName() );
        assertEquals( email, result.getEmail() );
        assertEquals( AccountStateType.REGISTER.getCode(), result.getAccountStateCode() );
    }

    @Test
    void findByEmailThrowsInvalidEmailFormatException() {
        String email = "invalidEmail";

        assertThrows( InvalidEmailFormatException.class,
            () -> accountService.findByEmail( email ) );
    }

    @Test
    void findByEmailThrowsNotFoundException() {
        String email = "nonExistentEmail@email.com";
        String id = "nonExistentId";

        when( accountRepository.findByEmail( email ) ).thenReturn( Optional.empty() );

        assertThrows( NotFoundException.class, () -> accountService.findByEmail( email ) );
    }

    @Test
    void updateByLastLoginAt() {
        String id = "normalId";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id( id )
            .build();

        when( accountRepository.findById( id ) ).thenReturn( Optional.of( mockAccountEntity ) );

        accountService.updateByLastLoginAt( id );

        verify( accountRepository, times( 1 ) ).save( any( AccountEntity.class ) );
    }

    @Test
    void updateByLastLoginAtThrowsNotFoundException() {
        String nonExistentId = "nonExistentId";

        when( accountRepository.findById( nonExistentId ) ).thenReturn( Optional.empty() );

        assertThrows( NotFoundException.class,
            () -> accountService.updateByLastLoginAt( nonExistentId ) );
    }

    @Test
    void get() {
        String id = "normalId";
        String email = "normalEmail@email.com";
        String password = "normalPassword";
        String name = "normalName";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id(id)
            .name(name)
            .password(password)
            .email(email)
            .build();

        AccountAccountStateEntity mockAccountStateEntity = AccountAccountStateEntity.builder()
            .account(mockAccountEntity)
            .pk(Pk.builder()
                .accountId(id)
                .accountStateCode(AccountStateType.REGISTER.getCode())
                .changeAt(LocalDateTime.now())
                .build())
            .build();

        when(accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(id))
            .thenReturn(Optional.of(mockAccountStateEntity));

        AccountResponse result = accountService.get(id);

        assertEquals(id, result.getId());
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(AccountStateType.REGISTER.getCode(), result.getAccountStateCode());
    }

    @Test
    void getThrowsNotFoundException() {
        String nonExistentId = "nonExistentId";

        when(accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(nonExistentId))
            .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> accountService.get(nonExistentId));
    }


    @Test
    void getAll() {
        String id = "normalId";
        String email = "normalEmail@email.com";
        String password = "normalPassword";
        String name = "normalName";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id(id)
            .name(name)
            .password(password)
            .email(email)
            .build();

        Page<AccountEntity> mockPage = new PageImpl<>(List.of(mockAccountEntity));

        when(accountRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = PageRequest.of(0, 1);
        Page<AccountResponse> result = accountService.getAll(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        AccountResponse response = result.getContent().get(0);
        assertEquals(id, response.getId());
        assertEquals(name, response.getName());
        assertEquals(email, response.getEmail());
    }
    @Test
    void getAllThrowsInvalidRequestException() {
        Page<AccountEntity> mockPage = new PageImpl<>( Collections.emptyList());

        when(accountRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Pageable pageable = PageRequest.of(0, 1);

        assertThrows( InvalidRequestException.class, () -> accountService.getAll(pageable));
    }


}