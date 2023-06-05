package com.nhn.minidooray.accountapi.domain.dto;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountDto {
  String id;
  String password;
  String name;
  String email;
  LocalDateTime lastLoginAt;
  LocalDateTime createdAt;
  String accountAccountStateCode;



}
