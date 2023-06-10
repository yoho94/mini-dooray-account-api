package com.nhn.minidooray.accountapi.domain.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountResponse {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;
    private String accountStateCode;
    private LocalDateTime accountStateChangeAt;
}
