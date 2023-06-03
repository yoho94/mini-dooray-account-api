package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;

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
  public void deactivation(AccountDto accountDto) {



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
