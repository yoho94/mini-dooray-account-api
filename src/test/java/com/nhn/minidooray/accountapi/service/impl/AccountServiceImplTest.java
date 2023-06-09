package com.nhn.minidooray.accountapi.service.impl;



import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.RequestInvalidException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertEquals("testUserId", accountService.find("testUserId"));


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
        assertThrows( RequestInvalidException.class, () -> accountService.updateName("testUserId",nameUpdateRequest));
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
        assertThrows( RequestInvalidException.class, () -> accountService.updateName("testUserId",passwordUpdateRequest));
    }
    @Test
    void getTopByIdWithChangeAt() {
        accountService.create( createAccountCreateRequest( "getTopById","getTopByIdPassword","getTopById@email.com","getTopBy" ) );

       assertEquals( "01", accountService.getTopByIdWithChangeAt( "getTopById" ).getAccountWithState().getStateCode());
    }

    @Test
    void findById() {
        String id = accountService.find("testUserId");
        assertEquals("testUserId", id);
    }

    @Test
    void findByEmail() {
        String id = accountService.findByEmail("testUserEmail@email.com");
        assertEquals("testUserId", id);
    }

    @Test
    void updateByLastLoginAt() {
      accountService.create( createAccountCreateRequest( "updateloginAt","updateloginAtpassword","updateloginAt@email.com","updateloginAt" ) );
      AccountUpdateRequest accountUpdateRequest= createAccountUpdateRequest(null,null,LocalDateTime.now());
      accountService.updateByLastLoginAt( "updateloginAt");

      AccountResponse updatedEntity = accountService.get("updateloginAt");
      assertNotNull( updatedEntity.getLastLoginAt());
    }

    @Test
    void getAllAccounts() {
        accountService.create( createAccountCreateRequest( "updateloginAt","updateloginAtpassword","updateloginAt@email.com","updateloginAt" ) );
        accountService.create( createAccountCreateRequest( "getTopById","getTopByIdPassword","getTopById@email.com","getTopBy" ) );

        assertEquals(accountService.getAll().size(), 3);
    }

    @Test
    @Transactional
    void deactivationById() {
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }
        accountService.deactivationById( "testUserId" );


        assertEquals( "02", accountService.getTopByIdWithChangeAt( "testUserId" ).getAccountWithState().getStateCode());
    }

    @Test
    void deactivationAllByAccounts() {
        String id1 = accountService.create( createAccountCreateRequest( "updateloginAt","updateloginAtpassword","updateloginAt@email.com","updateloginAt" ) );
        String id2 = accountService.create( createAccountCreateRequest( "getTopById","getTopByIdPassword","getTopById@email.com","getTopBy" ) );
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e) {
            throw new RuntimeException( e );
        }

       List<String> accountIds = List.of(id1,id2);
        accountService.deactivationAllByAccounts( accountIds );

        assertEquals( "02", accountService.getTopByIdWithChangeAt( id1 ).getAccountWithState().getStateCode());
        assertEquals( "02", accountService.getTopByIdWithChangeAt( id2 ).getAccountWithState().getStateCode());


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