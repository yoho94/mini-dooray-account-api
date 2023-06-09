package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStateCreateRequest {

    @NotEmpty
    @Size(max = 2)
    private String code;
    @NotEmpty
    private String name;


}
