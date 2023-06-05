package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateDetailService;
import com.nhn.minidooray.accountapi.service.AccountDetailService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * AccountDetailService imple은 AccountDetailDto에 AccountSerialDto를 임시로 넣어보았음.
 * 잘 동작하면 다른 것들도 추가할 예정
 */
@Service
@RequiredArgsConstructor
public class AccountDetailServiceImpl implements AccountDetailService {
    @Value("${com.nhn.minidooray.accountapi.validation.find-account}")
    private String findAccountMessage;

    private final AccountRepository accountRepository;
    private final AccountAccountStateRepository accountAccountStateRepository;
    private final AccountAccountStateDetailService accountAccountStateDetailService;

    private final AccountStateService accountStateService;

    /**
     * save시 반드시 accountDto에 .setAccountStateCode를 넣어주어야함.
     */

    @Override
    @Transactional
    public Optional<AccountDto> save(AccountCreateRequest accountCreateRequest) {
        AccountDto beforeAccountDto = convertAccountCreateRequestToAccountDto(accountCreateRequest);
        Optional<AccountDto> accountDto = Optional.of(convertToDto(accountRepository.save(convertToEntity(beforeAccountDto))));
        Optional<AccountDto> afterAccountDto = updateStatus(accountDto.get(), AccountStatus.REGISTERED.getStatusValue());

        return afterAccountDto;
    }

    /**
     * 이 업데이트는 회원 정보를 변경하는 기능입니다.
     */
    @Override
    public Optional<AccountDto> update(AccountDto accountDto) {
        Optional<AccountEntity> existedAccount = accountRepository.findById(accountDto.getId());
        if (existedAccount.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDto))));
    }

    @Override
    @Transactional
    public Optional<AccountDto> updateStatus(AccountDto accountDto, String statusCode) {
        Optional<AccountStateDto> accountStateDto = accountStateService.findByCode(statusCode);
        if (accountStateDto.isEmpty()) {
            return Optional.empty();
        }
        AccountAccountStateDetailDto.PkDto pkDto = AccountAccountStateDetailDto.PkDto
                .builder()
                .accountDto(accountDto)
                .accountStateDto(accountStateDto.get())
                .changeAt(LocalDateTime.now())
                .build();

        AccountAccountStateDetailDto accountAccountStateDetailDto = AccountAccountStateDetailDto
                .builder()
                .pkDto(pkDto)
                .build();
        accountDto.setAccountStateCode(accountAccountStateDetailDto.getPkDto().getAccountStateDto().getCode());
        accountAccountStateDetailService.save(accountAccountStateDetailDto);
        return Optional.of(accountDto);
    }

    @Override
    public Optional<AccountDto> findById(String id) {
        AccountEntity existedAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(findAccountMessage));

        // TODO Exception 따로 만들어서 일괄 처리 필요.
        AccountAccountStateEntity state = accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(id)
                .orElseThrow(() -> new NoSuchElementException("최근 상태 없음"));

        // TODO 못 찾은 경우에는 throw exception을 하고 advice에서 오류상황들 일괄처리
        //      찾은 경우만 return 하니 Optional 불필요
        AccountDto accountDto = convertToDto(existedAccount);
        accountDto.setAccountStateCode(state.getPk().getAccountStateCode());
        accountDto.setAccountAccountStateChangeAt(state.getPk().getChangeAt());

        return Optional.of(accountDto);
    }

    @Override
    public Optional<AccountDto> findByEmail(String email) {
        AccountEntity existedAccount = accountRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException(findAccountMessage));

        // TODO Exception 따로 만들어서 일괄 처리 필요.
        AccountAccountStateEntity state = accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(existedAccount.getId())
                .orElseThrow(() -> new NoSuchElementException("최근 상태 없음"));

        // TODO 못 찾은 경우에는 throw exception을 하고 advice에서 오류상황들 일괄처리
        //      찾은 경우만 return 하니 Optional 불필요
        AccountDto accountDto = convertToDto(existedAccount);
        accountDto.setAccountStateCode(state.getPk().getAccountStateCode());
        accountDto.setAccountAccountStateChangeAt(state.getPk().getChangeAt());

        return Optional.of(accountDto);
    }

    @Override
    @Transactional
    public void updateByLastLoginAt(String id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow();
        accountEntity.setLastLoginAt(LocalDateTime.now());
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deactivation(AccountAccountStateDetailDto accountAccountStateDetailDto) {
        accountAccountStateDetailService.save(accountAccountStateDetailDto);
    }

    @Transactional
    @Override
    public void deactivationById(String id) {

        AccountDto accountDto = accountRepository.findById(id).map(this::convertToDto).orElse(null);
        if (accountDto == null) {
            return;
        }
        updateStatus(accountDto, AccountStatus.DEACTIVATED.getStatusValue());
    }

    @Transactional
    @Override
    public void deactivationByEmail(String email) {
        AccountDto accountDto = accountRepository.findByEmail(email).map(this::convertToDto).orElse(null);
        if (accountDto == null) {
            return;
        }
        deactivationById(accountDto.getId());
    }

    @Transactional
    @Override
    public void deactivationAllByAccounts(List<AccountDto> accountDtos) {
        for (AccountDto accountDto : accountDtos) {
            deactivationById(accountDto.getId());
        }

    }

    private AccountDto convertToDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .id(accountEntity.getId())
                .name(accountEntity.getName())
                .email(accountEntity.getEmail())
                .password(accountEntity.getPassword())
                .createdAt(accountEntity.getCreateAt())
                .lastLoginAt(accountEntity.getLastLoginAt())
                .build();
    }

    private AccountEntity convertToEntity(AccountDto accountDto) {
        return AccountEntity.builder()
                .id(accountDto.getId())
                .name(accountDto.getName())
                .email(accountDto.getEmail())
                .password(accountDto.getPassword())
                .createAt(accountDto.getCreatedAt())
                .build();
    }

    private AccountDto convertAccountCreateRequestToAccountDto(AccountCreateRequest accountCreateRequest) {
        return AccountDto.builder()
                .name(accountCreateRequest.getName())
                .email(accountCreateRequest.getEmail())
                .password(accountCreateRequest.getPassword())
                .id(accountCreateRequest.getId())
                .build();
    }

}
