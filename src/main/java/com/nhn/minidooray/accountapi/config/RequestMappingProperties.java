package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.requestmapping")
@Getter
public class RequestMappingProperties {

    @Setter
    private String prefix;

    private final Account account = new Account();
    private final AccountState accountState = new AccountState();
    private final AccountAccountState accountAccountState = new AccountAccountState();

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

        private String deactAccount;
        private String deactAccountByEmailAccount;
        private String deactAccountAll;
        private String createAccountStateById;
        private String createAccountStateByEmail;
    }

    @Getter
    @Setter
    public static class AccountState {

        private String create;

        private String update;

        private String readList;
        private String readByCode;

        private String deleteByCode;

    }

    @Getter
    @Setter
    public static class AccountAccountState {

        private String create;
        private String readListByAccountIdAndAccountStateCode;

        private String readList;

        private String readListByAccountStateCode;

        private String deleteListByAccountIdAndAccountStateCode;

        private String deleteListByAccountId;

        private String deleteListByAccountStateCode;
    }
}
