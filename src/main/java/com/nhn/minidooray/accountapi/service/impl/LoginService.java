package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.request.LoginRequest;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.util.PasswordUtills;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
  private final AccountRepository accountRepository;


  public Optional<AccountEntity> loginValidate(LoginRequest loginRequest) {
    Optional<AccountEntity> account = accountRepository.findById(loginRequest.getId());

    if (account.get().getPassword().equals(PasswordUtills.encode(loginRequest.getPassword())))
      return Optional.of(account.get());

    return Optional.empty();
  }

}
