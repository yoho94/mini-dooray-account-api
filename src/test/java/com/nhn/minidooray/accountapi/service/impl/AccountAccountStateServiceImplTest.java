package com.nhn.minidooray.accountapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
class AccountAccountStateServiceImplTest {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger( this.getClass() );
    @Mock
    AccountAccountStateRepository accountAccountStateRepository;
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    private AccountAccountStateServiceImpl accountAccountStateService;


    @Test
    void create() {
        String accountId = "testId";
        String stateCode = "testStateCode";

        AccountEntity mockAccountEntity = AccountEntity.builder()
            .id(accountId)
            .name("testName")
            .email("testEmail")
            .password("testPassword")
            .build();

        AccountAccountStateEntity mockAccountAccountStateEntity = AccountAccountStateEntity.builder()
            .account(mockAccountEntity)
            .pk( Pk.builder()
                .accountStateCode(stateCode)
                .accountId(accountId)
                .changeAt( LocalDateTime.now())
                .build())
            .build();

        when(accountRepository.findById(anyString())).thenReturn( Optional.of(mockAccountEntity));
        when(accountAccountStateRepository.save(any(AccountAccountStateEntity.class))).thenReturn(mockAccountAccountStateEntity);

        accountAccountStateService.create(accountId, stateCode);

        verify(accountAccountStateRepository, times(1)).save(any(AccountAccountStateEntity.class));
    }
    @Test
    void createThrowsInvalidIdFormatException() {
        String accountId = "invalidId@email.com";
        String stateCode = "testStateCode";


        assertThrows(InvalidIdFormatException.class, () -> accountAccountStateService.create(accountId, stateCode));
    }

    @Test
    void createThrowsNotFoundException() {
        String accountId = "notExistId";
        String stateCode = "testStateCode";

        when(accountRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(
            NotFoundException.class, () -> accountAccountStateService.create(accountId, stateCode));
    }

    @Test
    void getByAccount_ValidId_ReturnsExpectedResults() {
        String accountId = "testId";
        Pageable pageable = PageRequest.of(0, 5);

        AccountAccountStateEntity entity = AccountAccountStateEntity.builder()
            .account(AccountEntity.builder().id(accountId).build())
            .pk(AccountAccountStateEntity.Pk.builder()
                .accountId(accountId)
                .accountStateCode("testStateCode")
                .changeAt(LocalDateTime.now())
                .build())
            .build();
        List<AccountAccountStateEntity> entityList = Arrays.asList(entity);
        Page<AccountAccountStateEntity> entities = new PageImpl<>(entityList, pageable, entityList.size());
        when(accountAccountStateRepository.findAllByAccount_IdOrderByPk_ChangeAt(accountId, pageable)).thenReturn(entities);

        Page<CommonAccountWithStateResponse> result = accountAccountStateService.getByAccount(accountId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(accountId, result.getContent().get(0).getAccountId());
    }

    @Test
    void getByAccount_InvalidId_ThrowsInvalidIdFormatException() {
        String accountId = "invalidId@email.com";
        Pageable pageable = PageRequest.of(0, 5);

        assertThrows(InvalidIdFormatException.class, () -> accountAccountStateService.getByAccount(accountId, pageable));
    }



}