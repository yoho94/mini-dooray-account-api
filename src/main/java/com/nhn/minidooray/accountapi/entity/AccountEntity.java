package com.nhn.minidooray.accountapi.entity;

import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;


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
    @Size(min = 8, max = 200)
    private String password;
    @Size(min = 1, max = 20)
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
        String name = accountUpdateRequest.getName();
        String password = accountUpdateRequest.getPassword();
        LocalDateTime lastLoginAt = accountUpdateRequest.getLastLoginAt();
        if (name != null) {
            this.name = name;
        }
        if (password != null) {
            this.password = password;
        }
        if (lastLoginAt != null) {
            this.lastLoginAt = lastLoginAt;
        }

    }
}
