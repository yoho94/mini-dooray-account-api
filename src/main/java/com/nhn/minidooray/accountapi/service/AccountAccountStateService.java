package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


public interface AccountAccountStateService {


    void create(String accountId, String stateCode);

    @Transactional
    Page<CommonAccountWithStateResponse> getByAccount(String accountId, Pageable pageable);

    void deleteAccountStateById(Pk pk);


}
