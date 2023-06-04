package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDetailDto;
import java.util.List;
import java.util.Optional;

public interface AccountDetailService {
  public Optional<AccountDetailDto> save(AccountDetailDto accountDetailDto);

  public Optional<AccountDetailDto> update(AccountDetailDto accountDetailDto);

  public Optional<AccountDetailDto> findById(String id);

  public Optional<AccountDetailDto> findByEmail(String email);

  public List<AccountDetailDto> findAll();

  public void deactivation(AccountAccountStateDto accountDetailDto);

  public void deactivationById(String id);

  public void deactivationByEmail(String email);

  public void deactivationAllByAccounts(List<AccountDetailDto> accountDetailDtos);
}
