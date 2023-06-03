package com.nhn.minidooray.accountapi.domain.dto;

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
public class AccountDetailDto {
  private String id;
  private String name;
  private String email;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdAt;
}
