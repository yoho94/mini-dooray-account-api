package com.nhn.minidooray.accountapi.domain.request;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountUpdateRequest {

    private String name;
    @Size(min = 8, max = 200)

    private String password;
    private LocalDateTime lastLoginAt;
}
