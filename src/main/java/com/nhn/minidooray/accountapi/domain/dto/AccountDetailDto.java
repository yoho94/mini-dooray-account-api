package com.nhn.minidooray.accountapi.domain.dto;

import java.time.LocalDateTime;
import javax.persistence.PrePersist;
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
@Deprecated(since = "삭제 예정")
public class AccountDetailDto {
  private AccountDetailSerialDto accountDetailSerialDto;
  private String name;
  private String email;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdAt;
  @PrePersist
  private void setCreateAt() {
    createdAt = LocalDateTime.now();
  }
}
