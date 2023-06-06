package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountNameRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountPasswordRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.exception.RecentStateException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountDetailService imple은 AccountDetailDto에 AccountSerialDto를 임시로 넣어보았음. 잘 동작하면 다른 것들도 추가할 예정
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountAccountStateRepository accountAccountStateRepository;
    private final AccountAccountStateService accountAccountStateService;

    private final AccountStateService accountStateService;

    /**
     * TODO ACCOUNT SAVE시 EMAIL 패턴 검사 해야함..
     */

    @Override
    @Transactional
    public AccountDto save(AccountCreateRequest accountCreateRequest) {
        AccountDto beforeAccountDto = convertAccountCreateRequestToAccountDto(accountCreateRequest);
        // 데이터베이스에 저장되기 직전에 createdAt 추가
        beforeAccountDto.setCreatedAt(LocalDateTime.now());

        if (accountRepository.existsById(accountCreateRequest.getId())) {
            throw new DataAlreadyExistsException(accountCreateRequest.getId());
        }
        if (accountRepository.existsByEmail(accountCreateRequest.getEmail())) {
            throw new DataAlreadyExistsException(accountCreateRequest.getEmail());
        }

        AccountEntity entity = accountRepository.save(convertToEntity(beforeAccountDto));

        return updateStatus(convertToDto(entity), AccountStatus.REGISTERED.getStatusValue());
    }

    /**
     * 이 업데이트는 회원 정보를 변경하는 기능입니다.
     */
    @Override
    public AccountDto update(AccountDto accountDto) {
        if (accountRepository.existsById(accountDto.getId())) {
            return convertToDto(accountRepository.save(convertToEntity(accountDto)));
        }
        throw new IllegalStateException("");
    }

    @Override
    public AccountDto updateNameById(ModifyAccountNameRequest modifyAccountNameRequest) {
        AccountEntity existedAccount = accountRepository.findById(
                modifyAccountNameRequest.getIdOrEmail())
            .orElseThrow(() -> new DataNotFoundException(modifyAccountNameRequest.getIdOrEmail()));
        existedAccount.setName(modifyAccountNameRequest.getName());

        return convertToDto(accountRepository.save(existedAccount));

    }

    @Override
    public AccountDto updateNameByEmail(ModifyAccountNameRequest modifyAccountNameRequest) {
        AccountEntity existedAccount = accountRepository.findByEmail(
                modifyAccountNameRequest.getIdOrEmail())
            .orElseThrow(() -> new DataNotFoundException(modifyAccountNameRequest.getIdOrEmail()));
        existedAccount.setName(modifyAccountNameRequest.getName());

        return convertToDto(accountRepository.save(existedAccount));

    }

    @Override
    public AccountDto updatePasswordById(
        ModifyAccountPasswordRequest modifyAccountPasswordRequest) {
        AccountEntity existedAccount = accountRepository.findById(
                modifyAccountPasswordRequest.getIdOrEmail())
            .orElseThrow(
                () -> new DataNotFoundException(modifyAccountPasswordRequest.getIdOrEmail()));
        existedAccount.setPassword(modifyAccountPasswordRequest.getPassword());

        return convertToDto(accountRepository.save(existedAccount));
    }

    @Override
    public AccountDto updatePasswordByEmail(
        ModifyAccountPasswordRequest modifyAccountPasswordRequest) {
        AccountEntity existedAccount = accountRepository.findByEmail(
                modifyAccountPasswordRequest.getIdOrEmail())
            .orElseThrow(
                () -> new DataNotFoundException(modifyAccountPasswordRequest.getIdOrEmail()));
        existedAccount.setPassword(modifyAccountPasswordRequest.getPassword());
        return convertToDto(accountRepository.save(existedAccount));
    }

    @Override
    @Transactional
    public AccountDto updateStatus(AccountDto accountDto, String statusCode) {
        AccountStateDto accountStateDto = accountStateService.findByCode(statusCode);

        AccountAccountStateDto.PkDto pkDto = AccountAccountStateDto.PkDto
            .builder()
            .accountId(accountDto.getId())
            .accountStateCode(accountStateDto.getCode())
            .changeAt(LocalDateTime.now())
            .build();

        AccountAccountStateDto accountAccountStateDto = AccountAccountStateDto
            .builder()
            .pkDto(pkDto)
            .build();
        accountDto.setAccountStateCode(accountAccountStateDto.getPkDto().getAccountStateCode());
        accountAccountStateService.save(accountAccountStateDto);
        return accountDto;
    }

    @Override
    @Transactional
    public AccountDto updateStatusById(String accountId, String statusCode) {
        AccountEntity existedAccount = accountRepository.findById(accountId)
            .orElseThrow(() -> new DataNotFoundException(accountId));

        return updateStatus(convertToDto(existedAccount), statusCode);
    }

    @Override
    @Transactional
    public AccountDto updateStatusByEmail(String email, String statusCode) {
        AccountEntity existedAccount = accountRepository.findByEmail(email)
            .orElseThrow(() -> new DataNotFoundException(email));

        return updateStatus(convertToDto(existedAccount), statusCode);
    }

    @Override
    public AccountDto findById(String id) {
        AccountEntity existedAccount = accountRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException(id));

        AccountAccountStateEntity state = accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(
                id)
            .orElseThrow(() -> new RecentStateException(id));

        AccountDto accountDto = convertToDto(existedAccount);
        accountDto.setAccountStateCode(state.getPk().getAccountStateCode());
        accountDto.setAccountAccountStateChangeAt(state.getPk().getChangeAt());

        return accountDto;
    }

    @Override
    public AccountDto findByEmail(String email) {
        AccountEntity existedAccount = accountRepository.findByEmail(email)
            .orElseThrow(() -> new DataNotFoundException(email));

        AccountAccountStateEntity state = accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(
                existedAccount.getId())
            .orElseThrow(() -> new RecentStateException(email));

        AccountDto accountDto = convertToDto(existedAccount);
        accountDto.setAccountStateCode(state.getPk().getAccountStateCode());
        accountDto.setAccountAccountStateChangeAt(state.getPk().getChangeAt());

        return accountDto;
    }

    @Override
    @Transactional
    public void updateByLastLoginAt(String id) {
        AccountEntity accountEntity = accountRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException(id));
        accountEntity.setLastLoginAt(LocalDateTime.now());
    }

    @Override
    public List<AccountDto> findAll() {
        return accountRepository.findAll().stream()
            .map(accountEntity -> {
                AccountDto accountDto = convertToDto(accountEntity);

                AccountAccountStateEntity stateEntity = accountAccountStateRepository
                    .findTopByAccount_IdOrderByPk_ChangeAtDesc(accountEntity.getId())
                    .orElseThrow(() -> new RecentStateException(accountEntity.getId()));

                accountDto.setAccountStateCode(stateEntity.getPk().getAccountStateCode());
                accountDto.setAccountAccountStateChangeAt(stateEntity.getPk().getChangeAt());

                return accountDto;
            })
            .collect(Collectors.toList());
    }

    //TODO DEACT 할때 이미 해당하는 상태이면 저장되지 않도록 구현해야함.
    @Override
    public void deactivation(AccountAccountStateDto accountAccountStateDto) {
        accountAccountStateService.save(accountAccountStateDto);
    }

    @Transactional
    @Override
    public void deactivationById(String id) {

        AccountDto accountDto = accountRepository.findById(id).map(this::convertToDto)
            .orElseThrow(() -> new DataNotFoundException(id));

        updateStatus(accountDto, AccountStatus.DEACTIVATED.getStatusValue());
    }

    @Transactional
    @Override
    public void deactivationByEmail(String email) {
        AccountDto accountDto = accountRepository.findByEmail(email).map(this::convertToDto)
            .orElseThrow(() -> new DataNotFoundException(email));

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

    private AccountDto convertAccountCreateRequestToAccountDto(
        AccountCreateRequest accountCreateRequest) {
        return AccountDto.builder()
            .name(accountCreateRequest.getName())
            .email(accountCreateRequest.getEmail())
            .password(accountCreateRequest.getPassword())
            .id(accountCreateRequest.getId())
            .build();
    }

}
