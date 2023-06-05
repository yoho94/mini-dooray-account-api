package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateDetailService;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAccountStateDetailServiceImpl implements AccountAccountStateDetailService {
  private final AccountAccountStateRepository accountAccountStateRepository;


  @Override
  public Optional<AccountAccountStateDetailDto> save(AccountAccountStateDetailDto accountAccountStateDetailDto) {
    return Optional.of(accountAccountStateRepository.save(convertToEntity(accountAccountStateDetailDto))).map(this::convertToDto);
  }

  @Override
  public Optional<AccountAccountStateDetailDto> update(AccountAccountStateDetailDto accountAccountStateDetailDto) {
    Optional<AccountAccountStateEntity> existed=accountAccountStateRepository.findById(convertToPk(accountAccountStateDetailDto.getPkDto()));
    if(existed.isEmpty()){
      return Optional.empty();
    }
    return existed.map(this::convertToDto);
  }

  @Override
  public List<AccountAccountStateDetailDto> findAllByAccountIdAndAccountStateCode(String accountId,
      String accountStateCode) {
    List<AccountAccountStateEntity> exists= new ArrayList<>(
        accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(accountId,
            accountStateCode));
    return exists.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public List<AccountAccountStateDetailDto> findAll() {
    return accountAccountStateRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public List<AccountAccountStateDetailDto> findAllByAccountStateCode(String accountStateCode) {
    return accountAccountStateRepository.findAllByAccountStateCode(accountStateCode).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public void delete(AccountAccountStateDetailDto accountAccountStateDetailDto) {
    Optional<AccountAccountStateEntity> existed=accountAccountStateRepository.findById(convertToPk(accountAccountStateDetailDto.getPkDto()));
    if(existed.isEmpty()){
      return;
    }
    accountAccountStateRepository.delete(convertToEntity(accountAccountStateDetailDto));

  }

  @Override
  public void deleteByAccountIdAndAccountStateCode(String accountId, String accountStateCode) {
    List<AccountAccountStateEntity> existed=accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(accountId, accountStateCode);
    if(existed.isEmpty()){
      return;
    }
    accountAccountStateRepository.deleteAll(existed);
  }

  @Override
  public void deleteAllByAccountId(String accountId) {
    List<AccountAccountStateEntity> existed=accountAccountStateRepository.findAllByAccount_Id(accountId);
    if(existed.isEmpty()){
      return;
    }
    accountAccountStateRepository.deleteAll(existed);
  }

  @Override
  public void deleteAllByAccountStateCode(String accountStateCode) {
    List<AccountAccountStateEntity> existed=accountAccountStateRepository.findAllByAccountStateCode(accountStateCode);
    if(existed.isEmpty()){
      return;
    }
    accountAccountStateRepository.deleteAll(existed);

  }

  private AccountAccountStateDetailDto convertToDto(AccountAccountStateEntity accountAccountStateEntity) {
    return AccountAccountStateDetailDto.builder()
        .pkDto(convertToPkDto(accountAccountStateEntity))
        .build();
  }
  private AccountAccountStateEntity convertToEntity(AccountAccountStateDetailDto accountAccountStateDetailDto) {
    return AccountAccountStateEntity.builder()
        .account(convertToAccountEntity(accountAccountStateDetailDto.getPkDto().accountDto))
        .accountState(convertToAccountStateEntity(accountAccountStateDetailDto.getPkDto().accountStateDto))
        .pk(convertToPk(accountAccountStateDetailDto.getPkDto()))
        .build();
  }
  private AccountAccountStateDetailDto.PkDto convertToPkDto(AccountAccountStateEntity accountAccountStateEntity) {
    return AccountAccountStateDetailDto.PkDto.builder()
        .accountDto(convertToAccountDto(accountAccountStateEntity.getAccount()))
        .accountStateDto(convertToAccountStateDto(accountAccountStateEntity.getAccountState()))
        .changeAt(accountAccountStateEntity.getPk().getChangeAt())
        .build();
  }
  private AccountAccountStateEntity.Pk convertToPk(AccountAccountStateDetailDto.PkDto pkDto) {
    return AccountAccountStateEntity.Pk.builder()
        .accountId(pkDto.getAccountDto().getId())
        .accountStateCode(pkDto.getAccountStateDto().getCode())
        .changeAt(pkDto.getChangeAt())
        .build();
  }
  private AccountDto convertToAccountDto(AccountEntity accountEntity) {
    return AccountDto.builder()
        .id(accountEntity.getId())
        .name(accountEntity.getName())
        .email(accountEntity.getEmail())
        .password(accountEntity.getPassword())
        .createdAt(accountEntity.getCreateAt())
        .lastLoginAt(accountEntity.getLastLoginAt())
        .build();
  }
  private AccountEntity convertToAccountEntity(AccountDto accountDto) {
    return AccountEntity.builder()
        .id(accountDto.getId())
        .name(accountDto.getName())
        .email(accountDto.getEmail())
        .password(accountDto.getPassword())
        .createAt(accountDto.getCreatedAt())
        .lastLoginAt(accountDto.getLastLoginAt())
        .build();
  }
  private AccountStateDto convertToAccountStateDto(AccountStateEntity accountStateEntity) {
    return AccountStateDto.builder()
        .code(accountStateEntity.getCode())
        .name(accountStateEntity.getName())
        .createAt(accountStateEntity.getCreateAt())
        .build();
  }

  private AccountStateEntity convertToAccountStateEntity(AccountStateDto accountStateDto) {
    return AccountStateEntity.builder()
        .code(accountStateDto.getCode())
        .name(accountStateDto.getName())
        .createAt(accountStateDto.getCreateAt())
        .build();
  }
}
