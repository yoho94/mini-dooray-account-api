package com.nhn.minidooray.accountapi.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountDto {
    String id;
    String password;
    String name;
    String email;
    LocalDateTime lastLoginAt;
    LocalDateTime createdAt;
    String accountStateCode;
    private LocalDateTime accountAccountStateChangeAt;
}
