package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.requestmapping")
@Getter
public class RequestMappingProperties {

    private final Account account = new Account();
    private final AccountAccountState accountAccountState = new AccountAccountState();
    @Setter
    private String prefix;

    @Getter
    @Setter
    public static class Account {

        private String createAccount;
        private String readAccountById;
        private String readAccountByEmail;
        private String readAccountList;
        private String readLastChangeAt;
        private String updateAccount;
        private String updateAccountName;
        private String updateAccountPasswordById;
        private String updateAccountPasswordByEmail;
        private String updateLastLoginAt;

    }


    @Getter
    @Setter
    public static class AccountAccountState {

        private String create;

        private String readListByAccountId;

    }
}
