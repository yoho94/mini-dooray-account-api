package com.nhn.minidooray.accountapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
class AccountAccountStateServiceImplTest {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger( this.getClass() );
    @Autowired
    private AccountAccountStateServiceImpl accountAccountStateService;

    @BeforeEach
    void setUp(){

    }
    @Test
    void create() {
    }

    @Test
    void getByAccount() {

    }

}