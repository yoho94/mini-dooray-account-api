package com.nhn.minidooray.accountapi.domain.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountUpdateRequest {

    private String name;
    @Size(max = 60)
    private String password;
    private LocalDateTime lastLoginAt;
    private String email;
}
