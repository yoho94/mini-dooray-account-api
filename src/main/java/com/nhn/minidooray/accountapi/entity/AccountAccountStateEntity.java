package com.nhn.minidooray.accountapi.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "ACCOUNT_ACCOUNT_STATE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountAccountStateEntity {
    @EmbeddedId
    private Pk pk;
    @MapsId("accountId")
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", nullable = false)
    private AccountEntity account;
    @MapsId("accountStateCode")
    @ManyToOne
    @JoinColumn(name = "ACCOUNT_STATE_CODE", referencedColumnName = "CODE", nullable = false)
    private AccountStateEntity accountState;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Generated
    public static class Pk implements Serializable {
        @Size(min = 5, max = 40)
        private String accountId;
        @Size(max = 2)
        private String accountStateCode;

        private LocalDateTime changeAt;
    }

}
