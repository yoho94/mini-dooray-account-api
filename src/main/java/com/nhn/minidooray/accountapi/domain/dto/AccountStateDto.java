package com.nhn.minidooray.accountapi.domain.dto;

import java.time.LocalDateTime;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Id
//@Max(2)
//private String code;
//@Max(45)
//private String name;
//private LocalDateTime createAt;
//@PrePersist
//private void setCreateAt() {
//    createAt = LocalDateTime.now();
//    }

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountStateDto {
private String code;
private String name;
private LocalDateTime createAt;
}
