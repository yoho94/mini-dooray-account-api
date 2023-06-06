package com.nhn.minidooray.accountapi.domain.dto;

import com.nhn.minidooray.accountapi.entity.AccountEntity;
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
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private String accountStateCode;
    private LocalDateTime accountAccountStateChangeAt;

}
