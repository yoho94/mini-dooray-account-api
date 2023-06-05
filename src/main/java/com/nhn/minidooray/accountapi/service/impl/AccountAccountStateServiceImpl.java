package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
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
    public AccountAccountStateDto save(AccountAccountStateDto accountAccountStateDto) {
        AccountAccountStateEntity entity = convertToEntity(accountAccountStateDto);

        entity.setAccount(
            accountRepository.getReferenceById(accountAccountStateDto.getPkDto().getAccountId()));
        entity.setAccountState(accountStateRepository.getReferenceById(
            accountAccountStateDto.getPkDto().getAccountStateCode()));

        return convertToDto(accountAccountStateRepository.save(entity));
    }

    @Override
    public AccountAccountStateDto update(AccountAccountStateDto accountAccountStateDto) {
        if (accountAccountStateRepository.existsById(
            convertToPk(accountAccountStateDto.getPkDto()))) {
            return convertToDto(
                accountAccountStateRepository.save(convertToEntity(accountAccountStateDto)));
        }
        throw new DataNotFoundException(accountAccountStateDto.getPkDto().getAccountStateCode(),
            accountAccountStateDto.getPkDto().getAccountId());
    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId,
        String accountStateCode) {
        List<AccountAccountStateEntity> exists = new ArrayList<>(
            accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(accountId,
                accountStateCode));
        return exists.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAll() {
        return accountAccountStateRepository.findAll().stream().map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode) {
        return accountAccountStateRepository.findAllByAccountStateCode(accountStateCode).stream()
            .map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(AccountAccountStateDto accountAccountStateDto) {
        if (accountAccountStateRepository.existsById(
            convertToPk(accountAccountStateDto.getPkDto()))) {
            accountAccountStateRepository.delete(convertToEntity(accountAccountStateDto));
        }
        throw new DataNotFoundException(accountAccountStateDto.getPkDto().getAccountStateCode(),
            accountAccountStateDto.getPkDto().getAccountId());
    }

    @Override
    public void deleteByAccountIdAndAccountStateCode(String accountId, String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(
            accountId, accountStateCode);
        if (existed.isEmpty()) {
            throw new DataNotFoundException(accountId, accountStateCode);
        }
        accountAccountStateRepository.deleteAll(existed);
    }

    @Override
    public void deleteAllByAccountId(String accountId) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_Id(
            accountId);
        if (existed.isEmpty()) {
            throw new DataNotFoundException(accountId);
        }
        accountAccountStateRepository.deleteAll(existed);
    }

    @Override
    public void deleteAllByAccountStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode);
        if (existed.isEmpty()) {
            throw new DataNotFoundException(accountStateCode);
        }
        accountAccountStateRepository.deleteAll(existed);

    }

    private AccountAccountStateDto convertToDto(
        AccountAccountStateEntity accountAccountStateEntity) {
        return AccountAccountStateDto.builder()
            .pkDto(convertToPkDto(accountAccountStateEntity.getPk()))
            .build();
    }

    private AccountAccountStateEntity convertToEntity(
        AccountAccountStateDto accountAccountStateDto) {
        return AccountAccountStateEntity.builder()
            .pk(convertToPk(accountAccountStateDto.getPkDto()))
            .build();
    }

    private AccountAccountStateDto.PkDto convertToPkDto(AccountAccountStateEntity.Pk pk) {
        return AccountAccountStateDto.PkDto.builder()
            .accountId(pk.getAccountId())
            .accountStateCode(pk.getAccountStateCode())
            .changeAt(pk.getChangeAt())
            .build();
    }

    private AccountAccountStateEntity.Pk convertToPk(AccountAccountStateDto.PkDto pkDto) {
        return AccountAccountStateEntity.Pk.builder()
            .accountId(pkDto.getAccountId())
            .accountStateCode(pkDto.getAccountStateCode())
            .changeAt(pkDto.getChangeAt())
            .build();
    }
}
