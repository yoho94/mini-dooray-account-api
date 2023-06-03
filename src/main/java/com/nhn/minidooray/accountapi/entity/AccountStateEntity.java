package com.nhn.minidooray.accountapi.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT_STATE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountStateEntity {
  @Id
  @Max(2)
  private String code;
  @Max(45)
  private String name;
  private LocalDateTime createAt;
  @PrePersist
  private void setCreateAt() {
    createAt = LocalDateTime.now();
  }

}
