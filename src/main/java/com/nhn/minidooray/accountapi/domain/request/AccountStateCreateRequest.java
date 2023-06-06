package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Generated
public class AccountStateCreateRequest {
    @NotEmpty
    @Size(max=2)
    private String code;
    @NotEmpty
    @Size(min=1,max=45)
    private String name;
}
