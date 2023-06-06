package com.nhn.minidooray.accountapi.service.impl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class AccountServiceImplTest {

    private static final Logger logger = Logger.getLogger(AccountServiceImplTest.class.getName());

    @Autowired
    private AccountServiceImpl accountService;


    @Test
    public void save() {
        AccountCreateRequest request = createAccountCreateRequest("testID2", "password2",
            "email2@email.com", "name2");
        AccountDto accountDto = accountService.save(request);
        assertThat(accountDto.getId()).isEqualTo("testID2");
    }

    @Test
    public void saveThrowException() {
        Exception exception1 = assertThrows(InvalidIdFormatException.class, () -> {
            AccountCreateRequest request = createAccountCreateRequest("testID2@email.com",
                "password2", "email2@email.com", "name2");
            AccountDto accountDto = accountService.save(request);
        });
        logger.info("LOG : " + exception1.getMessage());

        assertTrue(exception1.getMessage().equals("error.ID format is invalid"));

        Exception exception2 = assertThrows(InvalidEmailFormatException.class, () -> {
            AccountCreateRequest request = createAccountCreateRequest("testID", "password2",
                "email2", "name2");
            AccountDto accountDto = accountService.save(request);

        });
        logger.info("LOG : " + exception1.getMessage());

        assertTrue(exception2.getMessage().equals("error.Email format is invalid"));

        Exception exception3 = assertThrows(DataAlreadyExistsException.class, () -> {
            AccountCreateRequest request = createAccountCreateRequest("testID", "password2",
                "email@email.com", "name2");
            AccountDto accountDto = accountService.save(request);
        });
        logger.info("LOG : " + exception3.getMessage());

        assertTrue(exception3.getMessage().contains("testID"));
    }

    @Test
    void saveAndFindAll() {
        AccountCreateRequest request = createAccountCreateRequest("testID2", "password2",
            "email2@email.com", "name2");

        accountService.save(request);

        List<AccountDto> accountDtos = accountService.findAll();
        for (AccountDto accountDto : accountDtos) {
            logger.info(
                accountDto.getId() + " " + accountDto.getEmail() + " " + accountDto.getName() + " "
                    + accountDto.getAccountStateCode() + " " + accountDto.getLastLoginAt() + " "
                    + accountDto.getCreatedAt());
        }
        assertNotNull(accountDtos);
        assertEquals(5, accountDtos.size());
        AccountDto accountDto1 = accountDtos.get(0);

    }

    private AccountEntity createAccountEntity(String id, String password, String email, String name,
        LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        return AccountEntity.builder()
            .id(id)
            .password(password)
            .email(email)
            .name(name)
            .lastLoginAt(lastLoginAt)
            .createAt(createdAt)
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
            .pk(Pk.builder()
                .accountId(account.getId())
                .accountStateCode(accountState.getCode())
                .changeAt(changeAt)
                .build())
            .build();
    }

    private AccountCreateRequest createAccountCreateRequest(String id, String password,
        String email, String name) {
        return AccountCreateRequest.builder()
            .email(email)
            .id(id)
            .name(name)
            .password(password)
            .build();
    }
}