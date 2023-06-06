package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.exception.AccountWithStateNotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.ReferencedColumnException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountAccountStateServiceImpl implements AccountAccountStateService {

    private final AccountAccountStateRepository accountAccountStateRepository;
    private final AccountRepository accountRepository;
    private final AccountStateRepository accountStateRepository;


    @Override
    public AccountAccountStateDto save(AccountAccountCreateRequest accountAccountCreateRequest) {
        String idOrEmail = accountAccountCreateRequest.getIdOrEmail();
        String accountStateCode = accountAccountCreateRequest.getAccountStateCode();

        if (!IdOrEmailUtills.checkId(idOrEmail)) {
            throw new InvalidIdFormatException();
        }

        AccountAccountStateDto accountAccountStateDto = convertToDto(accountAccountCreateRequest);

        AccountAccountStateEntity entity = convertToEntity(accountAccountStateDto);

        entity.setAccount(accountRepository.getReferenceById(idOrEmail));
        entity.setAccountState(accountStateRepository.getReferenceById(accountStateCode));

        if (entity.getAccountState() == null) {
            throw new ReferencedColumnException(accountAccountCreateRequest.
                getAccountStateCode());
        }
        if (entity.getAccount() == null) {
            throw new ReferencedColumnException(accountAccountCreateRequest
                .getIdOrEmail());
        }

        return convertToDto(accountAccountStateRepository.save(entity));
    }

    @Override
    public AccountAccountStateDto update(AccountAccountStateDto accountAccountStateDto) {
        if (!accountAccountStateRepository.existsById(convertToPk(accountAccountStateDto))) {
            throw new AccountWithStateNotFoundException();
        }
        return convertToDto(
            accountAccountStateRepository.save(convertToEntity(accountAccountStateDto)));

    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId,
        String accountStateCode) {
        List<AccountAccountStateEntity> entities = accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(
            accountId, accountStateCode);

        if (entities.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }

        return entities.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAll() {

        return accountAccountStateRepository
            .findAll()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> exists = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode);
        if (exists.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }
        return exists
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(AccountAccountStateDto accountAccountStateDto) {
        AccountAccountStateEntity.Pk pk = convertToPk(accountAccountStateDto);
        if (!accountAccountStateRepository.existsById(pk)) {
            throw new AccountWithStateNotFoundException();
        }
        accountAccountStateRepository.delete(convertToEntity(accountAccountStateDto));


    }

    @Override
    public void deleteAllByAccountIdAndAccountStateCode(String accountId, String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(
            accountId, accountStateCode);

        if (existed.isEmpty()) {
            throw new AccountWithStateNotFoundException();

        }
        accountAccountStateRepository.deleteAll(existed);
    }

    @Override
    public void deleteAllByAccountId(String accountId) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_Id(
            accountId);

        if (existed.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }
        accountAccountStateRepository.deleteAll(existed);
    }

    @Override
    public void deleteAllByAccountStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode);

        if (existed.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }

        accountAccountStateRepository.deleteAll(existed);

    }

    private AccountAccountStateDto convertToDto(
        AccountAccountStateEntity accountAccountStateEntity) {
        return AccountAccountStateDto
            .builder()
            .accountId(accountAccountStateEntity.getPk().getAccountId())
            .accountStateCode(accountAccountStateEntity.getPk().getAccountStateCode())
            .changeAt(accountAccountStateEntity.getPk().getChangeAt())
            .build();
    }

    private AccountAccountStateDto convertToDto(
        AccountAccountCreateRequest accountAccountCreateRequest) {
        return AccountAccountStateDto
            .builder()
            .accountId(accountAccountCreateRequest.getIdOrEmail())
            .accountStateCode(accountAccountCreateRequest.getAccountStateCode())
            .changeAt(LocalDateTime.now())
            .build();
    }

    private AccountAccountStateEntity convertToEntity(
        AccountAccountStateDto accountAccountStateDto) {
        return AccountAccountStateEntity.builder()
            .pk(Pk.builder()
                .accountId(accountAccountStateDto.getAccountId())
                .accountStateCode(accountAccountStateDto.getAccountStateCode())
                .changeAt(accountAccountStateDto.getChangeAt()).build())
            .build();
    }


    private AccountAccountStateEntity.Pk convertToPk(
        AccountAccountStateDto accountAccountStateDto) {
        return AccountAccountStateEntity.Pk
            .builder()
            .accountId(accountAccountStateDto.getAccountId())
            .accountStateCode(accountAccountStateDto.getAccountStateCode())
            .changeAt(accountAccountStateDto.getChangeAt())
            .build();
    }


}
