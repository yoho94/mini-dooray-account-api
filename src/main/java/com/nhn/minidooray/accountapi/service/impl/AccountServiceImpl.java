package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
  @Value("${com.nhn.minidooray.accountapi.validation.find-account}")
  private String findAccountMessage;

  private final AccountRepository accountRepository;
  private final AccountAccountStateService accountAccountStateService;

  private final AccountStateService accountStateService;

  /**
   * save시 반드시 accountDto에 .setAccountStateCode를 넣어주어야함.
   */

  @Override
  @Transactional
  public Optional<AccountDto> save(AccountCreateRequest accountCreateRequest) {
    AccountDto beforeAccountDto=convertAccountCreateRequestToAccountDto(accountCreateRequest);
    Optional<AccountDto>afterAccountDto=updateStatus(beforeAccountDto,AccountStatus.REGISTERED.getStatusValue());

    return Optional.of(convertToDto(accountRepository.save(convertToEntity(afterAccountDto.get()))));
  }

  /**
   * 이 업데이트는 회원 정보를 변경하는 기능입니다.
   */
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
    accountDto.setAccountStateCode(accountAccountStateDto.getPkDto().getAccountStateCode());
    accountAccountStateService.save(accountAccountStateDto);
    return Optional.of(accountDto);
  }

  @Override
  public Optional<AccountDto> findById(String id) {
    Optional<AccountEntity> existedAccount=accountRepository.findById(id);
    if(existedAccount.isEmpty()){
      throw new IllegalStateException(findAccountMessage);
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
        .password(accountEntity.getPassword())
        .createdAt(accountEntity.getCreateAt())
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
  private AccountDto convertAccountCreateRequestToAccountDto(AccountCreateRequest accountCreateRequest) {
    return AccountDto.builder()
        .name(accountCreateRequest.getName())
        .email(accountCreateRequest.getEmail())
        .password(accountCreateRequest.getPassword())
        .id(accountCreateRequest.getId())
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
