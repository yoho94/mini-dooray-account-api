package com.nhn.minidooray.accountapi.service.impl;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class AccountStateServiceImplTest {
    @Autowired
    AccountStateServiceImpl accountStateService;
    @BeforeEach
    void setUp(){
        AccountStateCreateRequest request= createAccountStateCreateRequest( "05","테스트");
        accountStateService.create( request );

    }
    @Test
    void createState() {
        AccountStateCreateRequest request= createAccountStateCreateRequest( "06","create");
        accountStateService.create( request );
        assertEquals("06",accountStateService.find( request.getCode() ));
    }

    @Test
    void updateState() {
//accountStateService.updateState( . )
    }

    @Test
    void getStateAll() {
    }

    @Test
    void findState() {
    }

    @Test
    void getState() {
    }

    @Test
    void deleteState() {
    }

    @Test
    void deleteStateAll() {
    }
    private AccountEntity createAccountEntity(String id, String password, String email, String name,
        LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        return AccountEntity.builder()
            .id(id)
            .password(password)
            .email(email)
            .name(name)
            .build();
    }

    private AccountStateEntity createAccountStateEntity(String code, String name,
        LocalDateTime createAt) {
        return AccountStateEntity.builder()
            .code(code)
            .name(name)
            .createAt(createAt)
            .build();
    }

    private AccountAccountStateEntity createAccountAccountStateEntity(
        AccountStateEntity accountState, AccountEntity account, LocalDateTime changeAt) {
        return AccountAccountStateEntity.builder()
            .accountState(accountState)
            .account(account)
            .pk( Pk.builder()
                .accountId(account.getId())
                .accountStateCode(accountState.getCode())
                .changeAt(changeAt)
                .build())
            .build();
    }
    private AccountStateCreateRequest createAccountStateCreateRequest(String code, String name) {
        return AccountStateCreateRequest.builder()
            .code(code)
            .name(name)
            .build();
    }
    private AccountCreateRequest createAccountCreateRequest(String id, String password,
        String email, String name) {
        AccountCreateRequest accountCreateRequest =  AccountCreateRequest
            .builder()
            .id(id)
            .password(password)
            .name(name)
            .email(email)
            .build();

        return accountCreateRequest;
    }
    private AccountUpdateRequest createAccountUpdateRequest(String name,String password, LocalDateTime lastLoginAt){
        AccountUpdateRequest accountUpdateNameRequest = AccountUpdateRequest
            .builder()
            .name(name)
            .password(password)
            .lastLoginAt(lastLoginAt)
            .build();
        ;
        accountUpdateNameRequest.setName(name);
        return accountUpdateNameRequest;
    }

}