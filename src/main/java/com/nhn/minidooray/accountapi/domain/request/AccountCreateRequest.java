package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// todo valid

/**
 * NotEmpty : Null 허용 X, 공백 허용 X
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountCreateRequest {
  @NotEmpty
  @Size(min = 5, max = 40)
  private String id;
  @NotEmpty
  @Size(min=8,max = 200)
  private String password;
  @NotEmpty
  @Size(min=5,max = 100)
  private String email;
  @NotEmpty
  @Size(min=1,max = 20)

  private String name;

}
