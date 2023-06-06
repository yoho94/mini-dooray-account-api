package com.nhn.minidooray.accountapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class ModifyAccountPasswordRequest {

    private String idOrEmail;
    private String password;

}
