package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountAccountCreateRequest {

    @NotEmpty
    @Size(min = 5, max = 100)
    private String accountId;
    @NotEmpty
    @Size(max = 2)
    private String accountStateCode;
}
