package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import java.util.List;


public interface AccountAccountStateService {

    AccountAccountStateDto save(AccountAccountCreateRequest accountAccountCreateRequest);

    AccountAccountStateDto update(AccountAccountStateDto accountAccountStateDto);

    List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId,
        String accountStateCode);

    List<AccountAccountStateDto> findAll();

    List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode);

    void delete(AccountAccountStateDto accountAccountStateDto);


    void deleteAllByAccountIdAndAccountStateCode(String accountId, String accountStateCode);


    void deleteAllByAccountId(String accountId);

    void deleteAllByAccountStateCode(String accountStateCode);


}
