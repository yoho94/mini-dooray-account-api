package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;

import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;

import com.nhn.minidooray.accountapi.domain.request.ModifyAccountNameRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountPasswordRequest;
import java.util.List;


public interface AccountService {

    AccountDto save(AccountCreateRequest accountCreateRequest);

    AccountDto update(AccountDto accountDto);
    AccountDto updateNameById(ModifyAccountNameRequest modifyAccountNameRequest);
    AccountDto updateNameByEmail(ModifyAccountNameRequest modifyAccountNameRequest);
    AccountDto updatePasswordById(ModifyAccountPasswordRequest modifyAccountPasswordRequest);

    AccountDto updatePasswordByEmail(ModifyAccountPasswordRequest modifyAccountPasswordRequest);

    AccountDto updateStatus(AccountDto accountDto, String statusCode);

    AccountDto updateStatusById(String accountId, String statusCode);


    AccountDto updateStatusByEmail(String email, String statusCode);

    AccountDto findById(String id);

    AccountDto findByEmail(String email);

    List<AccountDto> findAll();

    void deactivation(AccountAccountStateDto accountDto);

    void deactivationById(String id);

    void deactivationByEmail(String email);

    void deactivationAllByAccounts(List<AccountDto> accountDtos);

    void updateByLastLoginAt(String id);

}
