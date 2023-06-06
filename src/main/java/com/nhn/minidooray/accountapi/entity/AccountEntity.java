package com.nhn.minidooray.accountapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;


@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountEntity {

    @Id
    @Size(min = 5, max = 40,message = "${com.nhn.minidooray.accountapi.validation.account.id-size}")
    private String id;
    @Size(min = 8, max = 200,message = "${com.nhn.minidooray.accountapi.validation.account.password-size}")
    private String password;
    @Size(min = 1, max = 20,message = "${com.nhn.minidooray.accountapi.validation.account.name-size}")
    private String name;
    @Size(min = 5, max = 100,message = "${com.nhn.minidooray.accountapi.validation.account.email-size}")
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;

    @PrePersist
    private void setCreateAt() {
        createAt = LocalDateTime.now();
    }
}
