package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import java.util.Optional;

public interface AccountStateService {
  public Optional<AccountStateDto> save(AccountStateDto accountStateDto);

  public Optional<AccountStateDto> update(AccountStateDto accountStateDto);

  public Optional<AccountStateDto> findByCode(String code);


  public void delete(AccountStateDto accountStateDto);

  public void deleteByCode(String code);


  public void deleteAll();

}
