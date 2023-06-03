package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;

  @Override
  public Optional<AccountDto> save(AccountEntity accountEntity) {
    return Optional.of(convertToDto(accountRepository.save(accountEntity)));
  }

  @Override
  public Optional<AccountDto> update(AccountDto accountEntity) {
    Optional<AccountEntity> existedAccount=accountRepository.findById(accountEntity.getId());
    if(existedAccount.isEmpty()){
      return Optional.empty();
    }
    return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountEntity))));
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
  public void deactivation(AccountDto accountEntity) {



  }

  @Override
  public void deactivationById(String id) {

  }

  @Override
  public void deactivationByEmail(String email) {

  }

  @Override
  public void deactivationAll() {

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
}
