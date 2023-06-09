package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountCreateRequest {

    @NotEmpty
    @Size(min = 5, max = 40)
    private String id;
    @NotEmpty
    @Size(min = 8, max = 200)
    private String password;
    @NotEmpty
    @Size(min = 5, max = 100)
    private String email;
    @NotEmpty
    private String name;

}
