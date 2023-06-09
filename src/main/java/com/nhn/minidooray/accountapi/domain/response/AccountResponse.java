package com.nhn.minidooray.accountapi.domain.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {

    private String id;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;


}
