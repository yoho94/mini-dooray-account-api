package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.AccountWithStateResponse;
import java.util.List;


public interface AccountService {

    String create(AccountCreateRequest accountCreateRequest);

    String updateName(String id, AccountUpdateRequest accountUpdateNameRequest);

    String updatePassword(String id,
        AccountUpdateRequest accountUpdatePasswordRequest);

    AccountWithStateResponse getTopByIdWithChangeAt(String id);

    String find(String id);

    String findByEmail(String email);


    void updateByLastLoginAt(String id);

    AccountResponse get(String id);

    List<AccountResponse> getAll();

    void deactivationById(String id);

    void deactivationAllByAccounts(List<String> accountIds);


}
