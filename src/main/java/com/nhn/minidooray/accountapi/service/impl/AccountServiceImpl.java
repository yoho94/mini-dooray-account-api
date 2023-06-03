package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final AccountAccountStateRepository accountAccountStateRepository;

  private final AccountStateRepository accountStateRepository;

  @Override
  public Optional<AccountDto> save(AccountDto accountDto) {
    return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDto))));
  }

  @Override
  public Optional<AccountDto> update(AccountDto accountDto) {
    Optional<AccountEntity> existedAccount=accountRepository.findById(accountDto.getId());
    if(existedAccount.isEmpty()){
      return Optional.empty();
    }
    return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDto))));
  }

  @Override
  public Optional<AccountDto> findById(String id) {
    Optional<AccountEntity> existedAccount=accountRepository.findById(id);
    if(existedAccount.isEmpty()){
      return Optional.empty();
    }
    return existedAccount.map(this::convertToDto);

  }

  @Override
  public Optional<AccountDto> findByEmail(String email) {
    Optional<AccountEntity> existedAccount=accountRepository.findByEmail(email);
    if(existedAccount.isEmpty()){
      return Optional.empty();
    }
    return existedAccount.map(this::convertToDto);
  }

  @Override
  public List<AccountDto> findAll() {
    return accountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public void deactivation(AccountAccountStateDto accountAccountStateDto) {
    accountAccountStateRepository.save(convertToAccountAccountStateEntity(accountAccountStateDto));
  }

  @Override
  public void deactivationById(String id) {
    AccountDto accountDto=accountRepository.findById(id).map(this::convertToDto).orElse(null);
    if(accountDto==null){
      return;
    }
    AccountStateDto accountStateDto=accountStateRepository.findById("02").map(this::convertToAccountStateEntity).orElse(null);
    if(accountStateDto==null){
      return;
    }
    AccountAccountStateDto.PkDto accountAccountStateDtoPkDto= AccountAccountStateDto.PkDto
        .builder()
        .accountId(accountDto.getId())
        .accountStateCode(accountStateDto.getCode())
        .changeAt(LocalDateTime.now())
        .build();

    AccountAccountStateDto accountAccountStateDto=AccountAccountStateDto
    .builder()
      .pkDto(accountAccountStateDtoPkDto)
          .build();

    accountAccountStateRepository.save(convertToAccountAccountStateEntity(accountAccountStateDto));
  }

  @Override
  public void deactivationByEmail(String email) {
    AccountDto accountDto=accountRepository.findByEmail(email).map(this::convertToDto).orElse(null);
    if(accountDto==null){
      return;
    }
    deactivationById(accountDto.getId());
  }

  @Override
  public void deactivationAllByAccounts(List<AccountDto> accountDtos) {
    for(AccountDto accountDto:accountDtos){
      deactivationById(accountDto.getId());
    }

  }

  private AccountDto convertToDto(AccountEntity accountEntity) {
    return AccountDto.builder()
        .id(accountEntity.getId())
        .name(accountEntity.getName())
        .email(accountEntity.getEmail())

        .build();
  }
  private AccountEntity convertToEntity(AccountDto accountDto) {
    return AccountEntity.builder()
        .id(accountDto.getId())
        .name(accountDto.getName())
        .email(accountDto.getEmail())
        .build();
  }
  private AccountAccountStateDto convertToAccountAccountStateDto(AccountAccountStateEntity accountAccountStateEntity) {
    return AccountAccountStateDto.builder()
        .pkDto(convertToAccountAccountStatePkDto(accountAccountStateEntity.getPk()))
        .build();
  }
  private AccountAccountStateEntity convertToAccountAccountStateEntity(AccountAccountStateDto accountAccountStateDto) {
    return AccountAccountStateEntity.builder()
        .pk(convertToAccountAccountStatePk(accountAccountStateDto.getPkDto()))
        .build();
  }
  private AccountAccountStateEntity.Pk convertToAccountAccountStatePk(AccountAccountStateDto.PkDto pkDto) {
    return AccountAccountStateEntity.Pk.builder()
        .accountId(pkDto.getAccountId())
        .accountStateCode(pkDto.getAccountStateCode())
        .changeAt(pkDto.getChangeAt())
        .build();
  }

  private AccountAccountStateDto.PkDto convertToAccountAccountStatePkDto(AccountAccountStateEntity.Pk pk) {
    return AccountAccountStateDto.PkDto.builder()
        .accountId(pk.getAccountId())
        .accountStateCode(pk.getAccountStateCode())
        .changeAt(pk.getChangeAt())
        .build();
  }
  private AccountStateEntity convertToAccountStateDto(AccountStateDto accountStateDto) {
    return AccountStateEntity.builder()
        .code(accountStateDto.getCode())
        .name(accountStateDto.getName())
        .createAt(accountStateDto.getCreateAt())
        .build();
  }
  private AccountStateDto convertToAccountStateEntity(AccountStateEntity accountStateEntity) {
    return AccountStateDto.builder()
        .code(accountStateEntity.getCode())
        .name(accountStateEntity.getName())
        .createAt(accountStateEntity.getCreateAt())
        .build();
  }
}
