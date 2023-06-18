package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LoginRequest {

    @NotEmpty
    @Size(min = 5, max = 40)
    private String id;
    @NotEmpty
    @Size(max = 60)
    private String password;

}
