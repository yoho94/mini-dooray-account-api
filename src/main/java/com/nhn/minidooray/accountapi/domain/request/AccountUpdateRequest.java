package com.nhn.minidooray.accountapi.domain.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUpdateRequest {

    @Size(max = 20)
    private String name;
    @Size(max = 60)
    private String password;
    private LocalDateTime lastLoginAt;
    private String email;
}
