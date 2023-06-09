package com.nhn.minidooray.accountapi.service.impl;



import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidRequestException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class AccountServiceImplTest {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
    @Autowired
    AccountServiceImpl accountService;
    @BeforeEach
    void setUp() {
        accountService.create(createAccountCreateRequest("testUserId","testUserPassword","testUserEmail@email.com","testUserName"));
    }

    @Test
    void testCreateAccount() {
        AccountCreateRequest request = createAccountCreateRequest("testUserId","testUserPassword","testUserEmail@email.com","testUserName");

        accountService.create(request);
        assertEquals("testUserId", accountService.get("testUserId").getId());


    }
    @Test
    void testCreateAccountThrow(){
        // 아이디 입력 오류
        assertThrows(InvalidIdFormatException.class, () -> accountService.create(createAccountCreateRequest("testUserId@email.com","testUserPassword","testUserEmail@email.com","testUserName")));
        assertThrows(InvalidEmailFormatException.class, () -> accountService.create(createAccountCreateRequest("testUserId","testUserPassword","testUserEmai","testUserName")));

    }

    @Test
    void updateAccountName() {
        AccountUpdateRequest nameUpdateRequest= createAccountUpdateRequest("updateUserName",null,null);
        accountService.updateName("testUserId",nameUpdateRequest);

        assertEquals("updateUserName", accountService.get("testUserId").getName());

    }

    @Test
    void updateAccountNameThrow() {
        AccountUpdateRequest nameUpdateRequest= createAccountUpdateRequest("updateUserName","1234",null);
        assertThrows( InvalidRequestException.class, () -> accountService.updateName("testUserId",nameUpdateRequest));
    }

    @Test
    void updateAccountPassword() {
        AccountUpdateRequest passwordUpdateRequest= createAccountUpdateRequest(null,"123123124",null);
        accountService.updatePassword( "testUserId",passwordUpdateRequest);
        assertEquals( "testUserId", accountService.get("testUserId").getId());
    }
    @Test
    void updateAccountPasswordThrow() {
        AccountUpdateRequest passwordUpdateRequest= createAccountUpdateRequest("updateUserName","1234",null);
        assertThrows( InvalidRequestException.class, () -> accountService.updateName("testUserId",passwordUpdateRequest));
    }
    @Test
    void getTopByIdWithChangeAt() {
        accountService.create( createAccountCreateRequest( "getTopById","getTopByIdPassword","getTopById@email.com","getTopBy" ) );

       assertEquals( "01", accountService.getTopByIdWithChangeAt( "getTopById" ).getAccountWithState().getStateCode());
    }


    @Test
    void findByEmail() {
        AccountResponse response = accountService.findByEmail("testUserEmail@email.com");
        assertEquals("testUserId", response.getId());
    }

    @Test
    void updateByLastLoginAt() {
      accountService.create( createAccountCreateRequest( "updateloginAt","updateloginAtpassword","updateloginAt@email.com","updateloginAt" ) );
      AccountUpdateRequest accountUpdateRequest= createAccountUpdateRequest(null,null,LocalDateTime.now());
      accountService.updateByLastLoginAt( "updateloginAt");

      AccountResponse updatedEntity = accountService.get("updateloginAt");
      assertNotNull( updatedEntity.getLastLoginAt());
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