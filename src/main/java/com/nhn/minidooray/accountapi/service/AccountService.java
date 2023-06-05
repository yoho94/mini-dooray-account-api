package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import java.util.List;
import java.util.Optional;

/**
 * deactivation : 비활성화
 */

public interface AccountService {
  public Optional<AccountDto> save(AccountDto accountDto);

  public Optional<AccountDto> update(AccountDto accountDto);

  public Optional<AccountDto> updateStatus(AccountDto accountDto,String statusCode);

  public Optional<AccountDto> findById(String id);

  public Optional<AccountDto> findByEmail(String email);

  public List<AccountDto> findAll();

  public void deactivation(AccountAccountStateDto accountDto);

  public void deactivationById(String id);

  public void deactivationByEmail(String email);

  public void deactivationAllByAccounts(List<AccountDto> accountDtos);



}
