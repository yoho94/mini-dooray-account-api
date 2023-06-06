package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.validation")
@Getter
public class ValidationProperties {
    private Account account=new Account();
    private AccountState accountState=new AccountState();
    private AccountAccountState accountAccountState=new AccountAccountState();

    @Getter
    @Setter
    public static class Account {

        private String idSize;
        private String passwordSize;
        private String nameSize;
        private String emailSize;
    }
    @Getter
    @Setter
    public static class AccountState {
        private String codeSzie;
        private String nameSize;
    }
    @Getter
    @Setter
    public static class AccountAccountState {
        private String accountIdSize;

        private String accountStateCodeSize;
    }

}
