package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountStateServiceImpl implements AccountStateService {
  private final AccountStateRepository accountStateRepository;

  @Override
  public Optional<AccountStateDto> save(AccountStateDto accountStateDto) {
    return Optional.of(convertToDto(accountStateRepository.save(convertToEntity(accountStateDto))));
  }
/**
  * @deprecated
 */
  @Deprecated(since = "일반적인 경우에 업데이트 하지 않습니다.", forRemoval = true)
  @Override
  public Optional<AccountStateDto> update(AccountStateDto accountStateDto) {
    Optional<AccountStateEntity> existed=accountStateRepository.findById(accountStateDto.getCode());
    if(existed.isEmpty()){
      return Optional.empty();
    }
    return existed.map(this::convertToDto);
  }

  @Override
  public List<AccountStateDto> findAll() {
    return accountStateRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @Override
  public Optional<AccountStateDto> findByCode(String code) {
    return Optional.empty();
  }

  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  @Override
  public void delete(AccountStateDto accountStateDto) {
    Optional<AccountStateEntity> existed=accountStateRepository.findById(accountStateDto.getCode());
    if(existed.isEmpty()){
      return;
    }
    accountStateRepository.delete(existed.get());
  }
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  @Override
  public void deleteByCode(String code) {
    Optional<AccountStateEntity> existed=accountStateRepository.findById(code);
    if(existed.isEmpty()){
      return;
    }
    accountStateRepository.delete(existed.get());

  }
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  @Override
  public void deleteAll() {
    accountStateRepository.deleteAll();

  }

  private AccountStateDto convertToDto(AccountStateEntity accountStateEntity) {
    return AccountStateDto.builder()
        .code(accountStateEntity.getCode())
        .name(accountStateEntity.getName())
        .createAt(accountStateEntity.getCreateAt())
        .build();
  }

  private AccountStateEntity convertToEntity(AccountStateDto accountStateDto) {
    return AccountStateEntity.builder()
        .code(accountStateDto.getCode())
        .name(accountStateDto.getName())
        .createAt(accountStateDto.getCreateAt())
        .build();
  }
}
