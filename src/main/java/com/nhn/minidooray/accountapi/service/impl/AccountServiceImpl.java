package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final AccountRepository accountRepository;
  private final AccountAccountStateService accountAccountStateService;

  private final AccountStateService accountStateService;

  /**
   * save시 반드시 accountDto에 .setAccountStateCode를 넣어주어야함.
   */

  @Override
  @Transactional
  public Optional<AccountDto> save(AccountDto accountDto) {
    Optional<AccountDto>  account=Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDto))));
    if(account.isEmpty()){
      return Optional.empty();
    }

    Optional<AccountStateDto> accountStateDto = accountStateService.findByCode(AccountStatus.REGISTERED.getStatusValue());
    if (accountStateDto.isEmpty()) {
      return Optional.empty();
    }
    AccountAccountStateDto.PkDto pkDto = AccountAccountStateDto.PkDto.builder().accountId(account.get().getId()).accountStateCode(accountStateDto.get().getCode()).changeAt(LocalDateTime.now()).build();
    AccountAccountStateDto accountAccountStateDto = AccountAccountStateDto.builder().pkDto(pkDto).build();
    accountAccountStateService.save(accountAccountStateDto);


    account.get().setAccountAccountStateCode(accountAccountStateDto.getPkDto().getAccountStateCode());

    return account;
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
  @Transactional
  public Optional<AccountDto> updateStatus(AccountDto accountDto, String statusCode) {
    Optional<AccountStateDto> accountStateDto=accountStateService.findByCode(statusCode);
    if(accountStateDto.isEmpty()){
      return Optional.empty();
    }
    AccountAccountStateDto.PkDto pkDto=AccountAccountStateDto.PkDto
        .builder()
        .accountId(accountDto.getId())
        .accountStateCode(accountStateDto.get().getCode())
        .changeAt(LocalDateTime.now())
        .build();

    AccountAccountStateDto  accountAccountStateDto = AccountAccountStateDto
        .builder()
        .pkDto(pkDto)
        .build();
    accountDto.setAccountAccountStateCode(accountAccountStateDto.getPkDto().getAccountStateCode());
    accountAccountStateService.save(accountAccountStateDto);
    return Optional.of(accountDto);
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
    accountAccountStateService.save(accountAccountStateDto);
  }
  @Transactional
  @Override
  public void deactivationById(String id) {

    AccountDto accountDto=accountRepository.findById(id).map(this::convertToDto).orElse(null);
    if(accountDto==null){
      return;
    }
    updateStatus(accountDto, AccountStatus.DEACTIVATED.getStatusValue());
  }

  @Transactional
  @Override
  public void deactivationByEmail(String email) {
    AccountDto accountDto=accountRepository.findByEmail(email).map(this::convertToDto).orElse(null);
    if(accountDto==null){
      return;
    }
    deactivationById(accountDto.getId());
  }

  @Transactional
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
        .lastLoginAt(accountEntity.getLastLoginAt())
        .build();
  }
  private AccountEntity convertToEntity(AccountDto accountDto) {
    return AccountEntity.builder()
        .id(accountDto.getId())
        .name(accountDto.getName())
        .email(accountDto.getEmail())
        .lastLoginAt(LocalDateTime.now())
        .build();
  }
//  private AccountAccountStateDto convertToAccountAccountStateDto(AccountAccountStateEntity accountAccountStateEntity) {
//    return AccountAccountStateDto.builder()
//        .pkDto(convertToAccountAccountStatePkDto(accountAccountStateEntity.getPk()))
//        .build();
//  }
//  private AccountAccountStateEntity convertToAccountAccountStateEntity(AccountAccountStateDto accountAccountStateDto) {
//    return AccountAccountStateEntity.builder()
//        .pk(convertToAccountAccountStatePk(accountAccountStateDto.getPkDto()))
//        .build();
//  }
//  private AccountAccountStateEntity.Pk convertToAccountAccountStatePk(AccountAccountStateDto.PkDto pkDto) {
//    return AccountAccountStateEntity.Pk.builder()
//        .accountId(pkDto.getAccountId())
//        .accountStateCode(pkDto.getAccountStateCode())
//        .changeAt(pkDto.getChangeAt())
//        .build();
//  }
//
//  private AccountAccountStateDto.PkDto convertToAccountAccountStatePkDto(AccountAccountStateEntity.Pk pk) {
//    return AccountAccountStateDto.PkDto.builder()
//        .accountId(pk.getAccountId())
//        .accountStateCode(pk.getAccountStateCode())
//        .changeAt(pk.getChangeAt())
//        .build();
//  }
//  private AccountStateEntity convertToAccountStateDto(AccountStateDto accountStateDto) {
//    return AccountStateEntity.builder()
//        .code(accountStateDto.getCode())
//        .name(accountStateDto.getName())
//        .createAt(accountStateDto.getCreateAt())
//        .build();
//  }
//  private AccountStateDto convertToAccountStateEntity(AccountStateEntity accountStateEntity) {
//    return AccountStateDto.builder()
//        .code(accountStateEntity.getCode())
//        .name(accountStateEntity.getName())
//        .createAt(accountStateEntity.getCreateAt())
//        .build();
//  }
}
