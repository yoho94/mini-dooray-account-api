package com.nhn.minidooray.accountapi.entity;

import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Generated
public class AccountEntity implements Updatable<AccountUpdateRequest> {

    @Id
    @Size(min = 5, max = 40)
    private String id;
    @Size(max = 60)
    private String password;
    @Size(max = 20)
    private String name;
    @Size(min = 5, max = 100)
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createAt;

    @Builder
    public AccountEntity(String id, String password, String name, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    @PrePersist
    private void setCreateAt() {
        createAt = LocalDateTime.now();
    }

    @Override
    public void update(AccountUpdateRequest accountUpdateRequest) {
        if (accountUpdateRequest.getName() != null) {
            this.name = accountUpdateRequest.getName();
        }
        if (accountUpdateRequest.getPassword() != null) {
            this.password = accountUpdateRequest.getPassword();
        }
        if (accountUpdateRequest.getLastLoginAt() != null) {
            this.lastLoginAt = accountUpdateRequest.getLastLoginAt();
        }
        if (accountUpdateRequest.getEmail() != null) {
            this.email = accountUpdateRequest.getEmail();
        }

    }
}
