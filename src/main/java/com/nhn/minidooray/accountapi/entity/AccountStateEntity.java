package com.nhn.minidooray.accountapi.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
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
  @Size(max=2)
  private String code;
  @Size(max=45)
  private String name;
  @Column(name="CREATE_AT")
  private LocalDateTime createAt;
  @PrePersist
  private void setCreateAt() {
    createAt = LocalDateTime.now();
  }

}
