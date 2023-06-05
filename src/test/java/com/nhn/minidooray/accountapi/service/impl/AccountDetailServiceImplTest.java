package com.nhn.minidooray.accountapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.service.AccountAccountStateDetailService;
import com.nhn.minidooray.accountapi.service.AccountDetailService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AccountDetailServiceImplTest {
  @Autowired
  AccountAccountStateDetailService accountAccountStateDetailService;
  @Autowired
  AccountStateService accountStateService;

  @Autowired
  AccountDetailService accountDetailService;
  @Test
  void save() {
  }
  @Test
  void findById() {
    AccountDto accountDto = accountDetailService.findById("exampleID").get();
    assertEquals("exampleID", accountDto.getId());
  }

}