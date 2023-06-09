package com.nhn.minidooray.accountapi.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountWithStateResponse {

    private AccountResponse accountDetail;
    private CommonAccountWithStateResponse accountWithState;


}
