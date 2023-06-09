package com.nhn.minidooray.accountapi.domain.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountWithStateByAccountResponse {

    String accountId;
    List<CommonAccountWithStateResponse> changes;

}
