package com.nhn.minidooray.accountapi.domain.dto;

import java.time.LocalDateTime;

public class AccountAccountStateDto {
  public PkDto pkDto;
  public static class PkDto {
    public String accountId;
    public String accountStateCode;

    public LocalDateTime changeAt;
  }



}
