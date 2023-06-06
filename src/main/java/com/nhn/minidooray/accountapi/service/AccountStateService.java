package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;

import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import java.util.List;


public interface AccountStateService {

    AccountStateDto save(AccountStateCreateRequest accountStateCreateRequest);

    AccountStateDto update(AccountStateCreateRequest accountStateCreateRequest);

    AccountStateDto findByCode(String code);

    List<AccountStateDto> findAll();

    void delete(AccountStateDto accountStateDto);

    void deleteByCode(String code);

    void deleteAll();

}
