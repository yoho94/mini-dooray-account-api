package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountNameRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountPasswordRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.RecentStateException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    @Override
    @Transactional
    public AccountDto save(AccountCreateRequest accountCreateRequest) {
        String email = accountCreateRequest.getEmail();

        if (!IdOrEmailUtills.checkEmail(email)) {
            throw new InvalidEmailFormatException();
        }

        if (accountRepository.existsById(accountCreateRequest.getId())) {
            throw new DataAlreadyExistsException(accountCreateRequest.getId());
        }

        if (accountRepository.existsByEmail(email)) {
            throw new DataAlreadyExistsException(email);
        }

        AccountDto accountDto = convertAccountCreateRequestToAccountDto(accountCreateRequest);
        accountDto.setCreatedAt(LocalDateTime.now());

        AccountEntity entity = accountRepository.save(convertToEntity(accountDto));

        return updateStatus(convertToDto(entity), AccountStatus.REGISTERED.getStatusValue());
    }


    /**
     * 이 업데이트는 회원 정보를 변경하는 기능입니다.
     */
    @Override
    public AccountDto update(AccountDto accountDto) {
        String accountId = accountDto.getId();

        if (accountRepository.existsById(accountId)) {
            AccountEntity entity = convertToEntity(accountDto);
            AccountEntity updatedEntity = accountRepository.save(entity);
            return convertToDto(updatedEntity);
        }
        throw new DataNotFoundException(accountDto.getId());
    }

    @Override
    public AccountDto updateNameById(ModifyAccountNameRequest modifyAccountNameRequest) {
        String idOrEmail = modifyAccountNameRequest.getIdOrEmail();

        AccountEntity existedAccount = getAccountById(idOrEmail);

        existedAccount.setName(modifyAccountNameRequest.getName());

        AccountEntity updatedAccount = accountRepository.save(existedAccount);
        return convertToDto(updatedAccount);
    }


    @Override
    public AccountDto updateNameByEmail(ModifyAccountNameRequest modifyAccountNameRequest) {
        String email = modifyAccountNameRequest.getIdOrEmail();

        AccountEntity existedAccount = getAccountByEmail(email);

        existedAccount.setName(modifyAccountNameRequest.getName());

        AccountEntity updatedAccount = accountRepository.save(existedAccount);
        return convertToDto(updatedAccount);
    }


    public AccountDto updateName(ModifyAccountNameRequest modifyAccountNameRequest) {
        String idOrEmail = modifyAccountNameRequest.getIdOrEmail();
        AccountEntity existedAccount;

        if (IdOrEmailUtills.checkIdOrEmail(idOrEmail)) {
            existedAccount = accountRepository.findByEmail(idOrEmail)
                .orElseThrow(() -> new DataNotFoundException(idOrEmail));
        } else {
            existedAccount = accountRepository.findById(idOrEmail)
                .orElseThrow(() -> new DataNotFoundException(idOrEmail));
        }

        existedAccount.setName(modifyAccountNameRequest.getName());

        return convertToDto(accountRepository.save(existedAccount));
    }

    @Override
    public AccountDto updatePasswordById(
        ModifyAccountPasswordRequest modifyAccountPasswordRequest) {
        AccountEntity existedAccount = getAccountById(modifyAccountPasswordRequest.getIdOrEmail());

        existedAccount.setPassword(modifyAccountPasswordRequest.getPassword());

        return convertToDto(accountRepository.save(existedAccount));
    }

    @Override
    public AccountDto updatePasswordByEmail(
        ModifyAccountPasswordRequest modifyAccountPasswordRequest) {
        AccountEntity existedAccount = getAccountByEmail(modifyAccountPasswordRequest.getIdOrEmail());

        existedAccount.setPassword(modifyAccountPasswordRequest.getPassword());

        return convertToDto(accountRepository.save(existedAccount));
    }

    public AccountDto updatePassword(ModifyAccountPasswordRequest modifyAccountPasswordRequest) {
        AccountEntity entity;
        if (IdOrEmailUtills.checkIdOrEmail(modifyAccountPasswordRequest.getIdOrEmail())) {
            entity = accountRepository.findByEmail(modifyAccountPasswordRequest.getIdOrEmail())
                .orElseThrow(
                    () -> new DataNotFoundException(modifyAccountPasswordRequest.getIdOrEmail()));
        } else {
            entity = accountRepository.findById(modifyAccountPasswordRequest.getIdOrEmail())
                .orElseThrow(
                    () -> new DataNotFoundException(modifyAccountPasswordRequest.getIdOrEmail()));

        }
        entity.setPassword(modifyAccountPasswordRequest.getPassword());
        return convertToDto(accountRepository.save(entity));

    }

    @Override
    @Transactional
    public AccountDto updateStatus(AccountDto accountDto, String statusCode) {
        AccountStateDto accountStateDto = accountStateService.findByCode(statusCode);

        AccountAccountStateDto accountAccountStateDto = AccountAccountStateDto
            .builder()
            .accountId(accountDto.getId())
            .accountStateCode(accountStateDto.getCode())
            .changeAt(LocalDateTime.now())
            .build();

        accountDto.setAccountStateCode(accountAccountStateDto.getAccountStateCode());
        accountDto.setAccountAccountStateChangeAt(accountAccountStateDto.getChangeAt());

        accountAccountStateService.save(
            convertToAccountAccountCreateRequest(accountAccountStateDto));

        return accountDto;
    }

    @Override
    @Transactional
    public AccountDto updateStatusById(String accountId, String statusCode) {
        AccountEntity existedAccount = getAccountById(accountId);

        return updateStatus(convertToDto(existedAccount), statusCode);
    }

    @Override
    @Transactional
    public AccountDto updateStatusByEmail(String email, String statusCode) {
        AccountEntity existedAccount = getAccountByEmail(email);

        return updateStatus(convertToDto(existedAccount), statusCode);
    }

    @Override
    public AccountDto findById(String id) {
        AccountEntity existedAccount = getAccountById(id);

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
        if (!IdOrEmailUtills.checkEmail(email)) {
            throw new InvalidEmailFormatException();
        }
        AccountEntity existedAccount = getAccountByEmail(email);

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
        accountAccountStateService.save(
            convertToAccountAccountCreateRequest(accountAccountStateDto));
    }

    @Transactional
    @Override
    public void deactivationById(String id) {

        AccountEntity accountEntity = getAccountById(id);

        updateStatus(convertToDto(accountEntity), AccountStatus.DEACTIVATED.getStatusValue());
    }

    @Transactional
    @Override
    public void deactivationByEmail(String email) {
        AccountEntity accountEntity = getAccountByEmail(email);
        deactivationById(convertToDto(accountEntity).getId());
    }

    @Transactional
    @Override
    public void deactivationAllByAccounts(List<AccountDto> accountDtos) {
        for (AccountDto accountDto : accountDtos) {
            deactivationById(accountDto.getId());
        }

    }

    private AccountEntity getAccountById(String id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException(id));
    }

    private AccountEntity getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
            .orElseThrow(() -> new DataNotFoundException(email));
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
            .lastLoginAt(accountDto.getLastLoginAt())
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

    private AccountAccountCreateRequest convertToAccountAccountCreateRequest(
        AccountAccountStateDto accountAccountStateDto
    ) {
        AccountAccountCreateRequest accountAccountCreateRequest = new AccountAccountCreateRequest();
        accountAccountCreateRequest.setIdOrEmail(accountAccountStateDto.getAccountId());
        accountAccountCreateRequest.setAccountStateCode(
            accountAccountStateDto.getAccountStateCode());
        return accountAccountCreateRequest;
    }

}
