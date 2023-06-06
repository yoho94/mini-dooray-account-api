package com.nhn.minidooray.accountapi.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * CREATE TABLE IF NOT EXISTS `nhn_academy_15`.`ACCOUNT` ( `ID`            VARCHAR(40)  NOT NULL,
 * `PASSWORD`      VARCHAR(200) NOT NULL, `SALT`          VARCHAR(45)  NOT NULL, `NAME` VARCHAR(20)
 * NOT NULL, `EMAIL`         VARCHAR(100) NOT NULL, `LAST_LOGIN_AT` DATETIME     NULL, `CREATE_AT`
 * DATETIME     NOT NULL DEFAULT NOW(), PRIMARY KEY (`ID`) ) ENGINE = InnoDB;
 */
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
    @Size(min = 5, max = 40)
    private String id;
    @Size(min = 8, max = 200)
    private String password;
    @Size(min = 1, max = 20)
    private String name;
    @Size(min = 5, max = 100)
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;

    @PrePersist
    private void setCreateAt() {
        createAt = LocalDateTime.now();
    }
}
