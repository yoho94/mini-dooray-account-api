package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Generated
public class AccountAccountCreateRequest {

    // accountId -> idOrEmail
    @NotEmpty
    @Size(min = 5, max = 100)
    private String idOrEmail;
    @NotEmpty
    @Size(max = 2)
    private String accountStateCode;
}
