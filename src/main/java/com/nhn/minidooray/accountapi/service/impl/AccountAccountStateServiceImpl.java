package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.exception.AccountWithStateNotFoundException;
import com.nhn.minidooray.accountapi.exception.ReferencedColumnException;
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
        if (entity.getAccountState() == null) {
            throw new ReferencedColumnException(accountAccountStateDto.getPkDto()
                .getAccountStateCode());
        }
        if (entity.getAccount() == null) {
            throw new ReferencedColumnException(accountAccountStateDto.getPkDto()
                .getAccountId());
        }

        return convertToDto(accountAccountStateRepository.save(entity));
    }

    @Override
    public AccountAccountStateDto update(AccountAccountStateDto accountAccountStateDto) {
        if (accountAccountStateRepository.existsById(
            convertToPk(accountAccountStateDto.getPkDto()))) {
            return convertToDto(
                accountAccountStateRepository.save(convertToEntity(accountAccountStateDto)));
        }
        throw new AccountWithStateNotFoundException();

    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId,
        String accountStateCode) {
        List<AccountAccountStateEntity> exists = new ArrayList<>(
            accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(accountId,
                accountStateCode));
        if (exists.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }
        return exists.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAll() {
        return accountAccountStateRepository.findAll().stream().map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> exists = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode);
        if (exists.isEmpty()) {
            throw new AccountWithStateNotFoundException();
        }
        return exists.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(AccountAccountStateDto accountAccountStateDto) {
        if (accountAccountStateRepository.existsById(
            convertToPk(accountAccountStateDto.getPkDto()))) {
            accountAccountStateRepository.delete(convertToEntity(accountAccountStateDto));
        }
        throw new AccountWithStateNotFoundException();

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
        return AccountAccountStateDto.builder()
            .pkDto(convertToPkDto(accountAccountStateEntity.getPk())).build();
    }

    private AccountAccountStateEntity convertToEntity(
        AccountAccountStateDto accountAccountStateDto) {
        return AccountAccountStateEntity.builder()
            .pk(convertToPk(accountAccountStateDto.getPkDto())).build();
    }

    private AccountAccountStateDto.PkDto convertToPkDto(AccountAccountStateEntity.Pk pk) {
        return AccountAccountStateDto.PkDto.builder().accountId(pk.getAccountId())
            .accountStateCode(pk.getAccountStateCode()).changeAt(pk.getChangeAt()).build();
    }

    private AccountAccountStateEntity.Pk convertToPk(AccountAccountStateDto.PkDto pkDto) {
        return AccountAccountStateEntity.Pk.builder().accountId(pkDto.getAccountId())
            .accountStateCode(pkDto.getAccountStateCode()).changeAt(pkDto.getChangeAt()).build();
    }
}
