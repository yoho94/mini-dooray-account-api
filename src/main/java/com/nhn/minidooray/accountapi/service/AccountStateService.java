package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountStateResponse;
import java.util.List;


public interface AccountStateService {


    String create(AccountStateCreateRequest accountStateCreateRequest);


    String find(String code);


    List<AccountStateResponse> getAll();

    AccountStateResponse get(String code);

    void delete(String code);

    void deleteAll();

}
