package com.nhn.minidooray.accountapi.domain.dto;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto.PkDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountAccountStateDetailDto {
  public AccountAccountStateDetailDto.PkDto pkDto;
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  @Getter
  @Setter
  @EqualsAndHashCode
  @Generated
  public static class PkDto {
    public AccountDto accountDto;
    public AccountStateDto accountStateDto;

    public LocalDateTime changeAt;
  }

}
