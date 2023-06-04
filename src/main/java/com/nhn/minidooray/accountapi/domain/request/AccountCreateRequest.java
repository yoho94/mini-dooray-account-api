package com.nhn.minidooray.accountapi.domain.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

// todo valid

/**
 * NotEmpty : Null 허용 X, 공백 허용 X
 */
@Getter
@Setter
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
  @Max(20)
  private String name;

}
