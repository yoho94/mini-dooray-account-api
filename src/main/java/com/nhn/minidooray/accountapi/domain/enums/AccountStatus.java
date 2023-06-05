package com.nhn.minidooray.accountapi.domain.enums;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum AccountStatus {
  REGISTERED("01"),  // 가입 완료
  DEACTIVATED("02"),  // 탈퇴
  DORMANT("03"),  // 휴면
  ACTIVE("04"),
  ;  // 활동 중
  private final String statusValue;
  AccountStatus(String statusValue) {
    this.statusValue = statusValue;
  }
  public String getStatusValue() {
    return statusValue;
  }
}
