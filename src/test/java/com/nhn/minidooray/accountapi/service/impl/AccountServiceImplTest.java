package com.nhn.minidooray.accountapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import java.util.List;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
class AccountServiceImplTest {
    private static final Logger logger = Logger.getLogger(AccountServiceImplTest.class.getName());

@Autowired
AccountServiceImpl accountService;
    @Test
    void findAll() {
        List<AccountDto> accountDtos = accountService.findAll();

        for(AccountDto accountDto : accountDtos) {
            logger.info(accountDto.getId() +" "+ accountDto.getEmail()+" "+ accountDto.getName()+" "+ accountDto.getAccountStateCode()+" "+ accountDto.getLastLoginAt()+" "+accountDto.getCreatedAt());
        }
    }
}