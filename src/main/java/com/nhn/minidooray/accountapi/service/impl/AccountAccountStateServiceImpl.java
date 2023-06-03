package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAccountStateServiceImpl implements AccountAccountStateService {

  private final AccountAccountStateRepository accountAccountStateRepository;

  @Override
  public Optional<AccountAccountStateDto> save(AccountAccountStateDto accountAccountStateDto) {
    return Optional.of(accountAccountStateRepository.save(convertToEntity(accountAccountStateDto))).map(this::convertToDto);
  }

  @Override
  public Optional<AccountAccountStateDto> update(AccountAccountStateDto accountAccountStateDto) {
    Optional<AccountAccountStateEntity> existed=accountAccountStateRepository.findById(convertToPk(accountAccountStateDto.getPkDto()));
    if(existed.isEmpty()){
      return Optional.empty();
    }
    return existed.map(this::convertToDto);
  }

  @Override
  public List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId,
      String accountStateCode) {
    List<AccountAccountStateEntity> exists= new ArrayList<>(
        accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(accountId,
            accountStateCode));
    return exists.stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public List<AccountAccountStateDto> findAll() {
    return accountAccountStateRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode) {
    return accountAccountStateRepository.findAllByAccountStateCode(accountStateCode).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public void delete(AccountAccountStateDto accountAccountStateDto) {
    Optional<AccountAccountStateEntity> existed=accountAccountStateRepository.findById(convertToPk(accountAccountStateDto.getPkDto()));
    if(existed.isEmpty()){
      return;
    }
    accountAccountStateRepository.delete(convertToEntity(accountAccountStateDto));

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

  private AccountAccountStateDto convertToDto(AccountAccountStateEntity accountAccountStateEntity) {
    return AccountAccountStateDto.builder()
        .pkDto(convertToPkDto(accountAccountStateEntity.getPk()))
        .build();
  }
  private AccountAccountStateEntity convertToEntity(AccountAccountStateDto accountAccountStateDto) {
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
