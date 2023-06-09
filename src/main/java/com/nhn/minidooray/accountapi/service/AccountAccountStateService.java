package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.response.AccountWithStateByAccountResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


public interface AccountAccountStateService {


    void create(String accountId, String stateCode);

    @Transactional
    AccountWithStateByAccountResponse getByAccount(String accountId);

    List<CommonAccountWithStateResponse> getAllByAccountIdAndAccountStateCode(String accountId,
        String stateCode);

    List<CommonAccountWithStateResponse> getAll();

    List<CommonAccountWithStateResponse> getByAccountStateCode(String stateCode);

    void deleteAccountStateById(Pk pk);

    void deleteAllByAccountIdAndAccountStateCode(String accountId, String stateCode);


    void deleteAllByAccountId(String accountId);

    void deleteAllByStateCode(String stateCode);


}
