package com.nhn.minidooray.accountapi.domain.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountStateResponse {

    private String code;
    private String name;
    private LocalDateTime createAt;


}
