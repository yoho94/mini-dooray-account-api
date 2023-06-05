package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;

import java.util.List;


public interface AccountStateService {

    AccountStateDto save(AccountStateDto accountStateDto);

    AccountStateDto update(AccountStateDto accountStateDto);

    AccountStateDto findByCode(String code);

    List<AccountStateDto> findAll();

    void delete(AccountStateDto accountStateDto);

    void deleteByCode(String code);

    void deleteAll();

}
