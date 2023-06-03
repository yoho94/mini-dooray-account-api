package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.entity.AccountEntity;
import java.util.Optional;

public interface AccountService {
  public Optional<AccountEntity> save(AccountEntity accountEntity);

  public Optional<AccountEntity> update(AccountEntity accountEntity);

  public Optional<AccountEntity> findById(String id);

  public Optional<AccountEntity> findByEmail(String email);

  public void delete(AccountEntity accountEntity);

  public void deleteById(String id);

  public void deleteByEmail(String email);

  public void deleteAll();



}
