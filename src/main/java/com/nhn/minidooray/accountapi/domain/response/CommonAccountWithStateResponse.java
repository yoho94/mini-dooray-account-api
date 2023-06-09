package com.nhn.minidooray.accountapi.domain.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class CommonAccountWithStateResponse {

    private String accountId;
    private String stateCode;
    private LocalDateTime changeAt;
}
