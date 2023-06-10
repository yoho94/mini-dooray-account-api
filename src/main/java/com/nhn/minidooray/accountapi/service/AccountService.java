package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AccountService {

    void create(AccountCreateRequest accountCreateRequest);

    ResultResponse<Void> update(String id, AccountUpdateRequest request);

    void updateName(String id, AccountUpdateRequest accountUpdateNameRequest);

    void updatePassword(String id,
        AccountUpdateRequest accountUpdatePasswordRequest);

    void updateByLastLoginAt(String id);

    AccountResponse findByEmail(String email);

    AccountResponse get(String id);

    Page<AccountResponse> getAll(Pageable pageable);


}
