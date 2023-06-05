package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDetailDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import java.util.List;
import java.util.Optional;

public interface AccountDetailService {
  public Optional<AccountDto> save(AccountCreateRequest accountCreateRequest);

  public Optional<AccountDto> update(AccountDto accountDto);
  public Optional<AccountDto> updateStatus(AccountDto accountDto,String statusCode);

  public Optional<AccountDto> findById(String id);

  public Optional<AccountDto> findByEmail(String email);

  public List<AccountDto> findAll();

  public void deactivation(AccountAccountStateDto accountDto);

  public void deactivationById(String id);

  public void deactivationByEmail(String email);

  public void deactivationAllByAccounts(List<AccountDto> accountDtos);

  void updateByLastLoginAt(String id);

}
