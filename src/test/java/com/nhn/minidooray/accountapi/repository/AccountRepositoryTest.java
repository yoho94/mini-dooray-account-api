package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class AccountRepositoryTest {


    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountService accountService;

    @Autowired
    AccountStateRepository accountStateRepository;

    @Autowired
    AccountStateService accountStateService;

    @Autowired
    AccountAccountStateService accountAccountStateService;
//    @BeforeEach
//    void setUp() {
//        AccountStateDto accountStateDto1=AccountStateDto
//            .builder()
//            .code(AccountStatus.REGISTERED.getStatusValue())
//            .name("가입")
//            .createAt(LocalDateTime.now())
//            .build();
//        AccountStateDto accountStateDto2=AccountStateDto
//            .builder()
//            .code(AccountStatus.DEACTIVATED.getStatusValue())
//            .name("탈퇴")
//            .createAt(LocalDateTime.now())
//            .build();
//        AccountStateDto accountStateDto3=AccountStateDto
//            .builder()
//            .code(AccountStatus.DORMANT.getStatusValue())
//            .name("휴면")
//            .createAt(LocalDateTime.now())
//            .build();
//        AccountStateDto accountStateDto4=AccountStateDto
//            .builder()
//            .code(AccountStatus.ACTIVE.getStatusValue())
//            .name("활동")
//            .createAt(LocalDateTime.now())
//            .build();
//        accountStateService.save(accountStateDto1);
//        accountStateService.save(accountStateDto2);
//        accountStateService.save(accountStateDto3);
//        accountStateService.save(accountStateDto4);
//    }
    @Test
    void testSave(){
        AccountStateDto accountStateDto1=AccountStateDto
            .builder()
            .code(AccountStatus.REGISTERED.getStatusValue())
            .name("가입")
            .createAt(LocalDateTime.now())
            .build();
        AccountStateDto accountStateDto2=AccountStateDto
            .builder()
            .code(AccountStatus.DEACTIVATED.getStatusValue())
            .name("탈퇴")
            .createAt(LocalDateTime.now())
            .build();
        AccountStateDto accountStateDto3=AccountStateDto
            .builder()
            .code(AccountStatus.DORMANT.getStatusValue())
            .name("휴면")
            .createAt(LocalDateTime.now())
            .build();
        AccountStateDto accountStateDto4=AccountStateDto
            .builder()
            .code(AccountStatus.ACTIVE.getStatusValue())
            .name("활동")
            .createAt(LocalDateTime.now())
            .build();
        accountStateService.save(accountStateDto1);
        accountStateService.save(accountStateDto2);
        accountStateService.save(accountStateDto3);
        accountStateService.save(accountStateDto4);
        AccountCreateRequest accountCreateRequest=AccountCreateRequest
            .builder()
            .id("userId")
            .password("password")
            .email("email@gmail.com")
            .name("name")
            .build();

        // Act
        AccountDto createdAccount = accountService.save(accountCreateRequest).get();

        // Assert
        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getId()).isEqualTo(accountCreateRequest.getId());
        assertThat(createdAccount.getName()).isEqualTo(accountCreateRequest.getName());
        assertThat(createdAccount.getEmail()).isEqualTo(accountCreateRequest.getEmail());


    }
    @Test
    void testFindById(){

        // Arrange
        AccountCreateRequest accountCreateRequest=AccountCreateRequest
            .builder()
            .id("userId")
            .password("password")
            .email("email@gmail.com")
            .name("name")
            .build();
        AccountDto createdAccount = accountService.save(accountCreateRequest).get();
        AccountDto retrievedAccount = accountService.findById(createdAccount.getId()).get();

        List<AccountAccountStateDto> accountAccountStateDtos = accountAccountStateService.findAllByAccountIdAndAccountStateCode(createdAccount.getId(), AccountStatus.REGISTERED.getStatusValue());
        assertThat(accountAccountStateDtos.size()).isEqualTo(1);
    }


}