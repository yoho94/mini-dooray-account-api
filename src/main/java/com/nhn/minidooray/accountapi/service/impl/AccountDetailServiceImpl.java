package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDetailSerialDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountDetailService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AccountDetailService imple은 AccountDetailDto에 AccountSerialDto를 임시로 넣어보았음.
 * 잘 동작하면 다른 것들도 추가할 예정
 */
@Service
@RequiredArgsConstructor
public class AccountDetailServiceImpl implements AccountDetailService
{
  private final AccountRepository accountRepository;
  private final AccountAccountStateRepository accountAccountStateRepository;
  private final AccountStateRepository accountStateRepository;

  @Override
  public Optional<AccountDetailDto> save(AccountDetailDto accountDetailDto) {
    return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDetailDto))));
  }

  @Override
  public Optional<AccountDetailDto> update(AccountDetailDto accountDetailDto) {
    Optional<AccountEntity> accountEntity=accountRepository.findById(accountDetailDto.getAccountDetailSerialDto().getId());
    if(accountEntity.isEmpty()){
      return Optional.empty();
    }
    return Optional.of(convertToDto(accountRepository.save(convertToEntity(accountDetailDto))));
  }


  @Override
  public Optional<AccountDetailDto> findById(String id) {
    Optional<AccountEntity> existed=accountRepository.findById(id);
    if(existed.isEmpty()){
      return Optional.empty();
    }
    return Optional.of(convertToDto(existed.get()));
  }

  @Override
  public Optional<AccountDetailDto> findByEmail(String email) {
    Optional<AccountEntity> existed=accountRepository.findByEmail(email);
    if(existed.isEmpty()){
      return Optional.empty();
    }
    return Optional.of(convertToDto(existed.get()));
  }

  @Override
  public List<AccountDetailDto> findAll() {

    return accountRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public void deactivation(AccountAccountStateDto accountAccountStateDto) {
    accountAccountStateRepository.save(convertToAccountAccountStateEntity(accountAccountStateDto));
  }

  @Override
  public void deactivationById(String id) {
    AccountDetailDto accountDetailDto=accountRepository.findById(id).map(this::convertToDto).orElse(null);
    if(accountDetailDto==null){
      return;
    }
    AccountStateDto accountStateDto=accountStateRepository.findById("02").map(this::convertToAccountStateEntity).orElse(null);
    if(accountStateDto==null){
      return;
    }
    AccountAccountStateDto.PkDto accountAccountStateDtoPkDto= AccountAccountStateDto.PkDto
        .builder()
        .accountId(accountDetailDto.getAccountDetailSerialDto().getId())
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
    AccountDetailDto accountDetailDto=accountRepository.findByEmail(email).map(this::convertToDto).orElse(null);
    if(accountDetailDto==null){
      return;
    }
    deactivationById(accountDetailDto.getAccountDetailSerialDto().getId());
  }

  @Override
  public void deactivationAllByAccounts(List<AccountDetailDto> accountDetailDtos) {
    for(AccountDetailDto accountDetailDto:accountDetailDtos){
      deactivationById(accountDetailDto.getAccountDetailSerialDto().getId());
    }

  }
  private AccountDetailDto convertToDto(AccountEntity accountEntity) {
    return AccountDetailDto.builder()
        .accountDetailSerialDto(convertToAccountDetailSerialDto(accountEntity))
        .name(accountEntity.getName())
        .email(accountEntity.getEmail())
        .lastLoginAt(accountEntity.getLastLoginAt())
        .createdAt(accountEntity.getCreateAt())
        .build();
  }
  private AccountEntity convertToEntity(AccountDetailDto accountDetailDto) {
    return AccountEntity.builder()
        .id(accountDetailDto.getAccountDetailSerialDto().getId())
        .name(accountDetailDto.getName())
        .email(accountDetailDto.getEmail())
        .lastLoginAt(LocalDateTime.now())
        .createAt(accountDetailDto.getCreatedAt())
        .build();
  }
  private AccountAccountStateDto convertToAccountAccountStateDto(
      AccountAccountStateEntity accountAccountStateEntity) {
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
  private AccountDetailSerialDto convertToAccountDetailSerialDto(AccountEntity accountEntity) {
    return AccountDetailSerialDto.builder()
        .id(accountEntity.getId())
        .build();
  }
}
