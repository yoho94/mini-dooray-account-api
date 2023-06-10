package com.nhn.minidooray.accountapi.domain.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
