package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.request.LoginRequest;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.LoginFailException;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;


    public AccountEntity loginValidate(LoginRequest loginRequest) {
        if(!IdOrEmailUtills.checkId(loginRequest.getId())) {
            throw new InvalidIdFormatException();
        }

        AccountEntity account = accountRepository.findById(loginRequest.getId())
            .orElseThrow(() -> new LoginFailException(loginRequest.getId()));

        if (!account.getPassword().equals(loginRequest.getPassword())) {
            throw new LoginFailException(loginRequest.getId());
        }
        return account;
    }
}