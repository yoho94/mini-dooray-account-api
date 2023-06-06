package com.nhn.minidooray.accountapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class AccountServiceImplTest {

    private static final Logger logger = Logger.getLogger(AccountServiceImplTest.class.getName());
    @MockBean
    private AccountStateRepository accountStateRepository;
    @Autowired
    AccountServiceImpl accountService;

    @BeforeEach
    void setUp(){
    }

    @Test
    void findAll() {
        List<AccountDto> accountDtos = accountService.findAll();

        for (AccountDto accountDto : accountDtos) {
            logger.info(
                accountDto.getId() + " " + accountDto.getEmail() + " " + accountDto.getName() + " "
                    + accountDto.getAccountStateCode() + " " + accountDto.getLastLoginAt() + " "
                    + accountDto.getCreatedAt());
        }
    }

    private AccountEntity createAccountEntity(String id, String email, String name, LocalDateTime lastLoginAt, LocalDateTime createdAt) {

    }
}