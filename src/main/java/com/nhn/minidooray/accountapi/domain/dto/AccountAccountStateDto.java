package com.nhn.minidooray.accountapi.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Generated
public class AccountAccountStateDto {

    private PkDto pkDto;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @EqualsAndHashCode
    @Generated
    public static class PkDto {

        private String accountId;
        private String accountStateCode;

        private LocalDateTime changeAt;
    }


}
