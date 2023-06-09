package com.nhn.minidooray.accountapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
    @ToString
    public static class Pk implements Serializable {

        @Size(min = 5, max = 40)
        private String accountId;
        @Size(max = 2)
        private String accountStateCode;

        private LocalDateTime changeAt;
    }

}
