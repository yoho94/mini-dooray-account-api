package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.requestmapping")
@Getter
public class RequestMappingProperties {

    @Setter
    private String prefix;

    private Account account = new Account();
    private AccountState accountState = new AccountState();
    private AccountAccountState accountAccountState = new AccountAccountState();

    @Getter
    @Setter
    public static class Account {
        private String createAccount;
        private String readAccountById;
        private String readAccountByEmail;
        private String readAccountList;
        private String updateAccount;
        private String updateAccountNameById;
        private String updateAccountNameByEmail;
        private String updateAccountPasswordById;
        private String updateAccountPasswordByEmail;
        private String deactAccountByIdAccount;
        private String deactAccountByEmailAccount;
        private String deactAccountsByAll;
        private String createAccountStateById;
        private String createAccountStateByEmail;
    }
    @Getter
    @Setter
    public static class AccountState {
        private String createAccountState;

        private String updateAccountState;

        private String readAccountStateList;
        private String readAccountStateByCode;

        private String deleteAccountListStateByCode;
        private String deleteAccountListState;

    }
    @Getter
    @Setter
    public static class AccountAccountState {
        private String createAccountAccountState;
        private String readAccountAccountStateListByAccountIdAndAccountStateCode;

        private String readAccountAccountStateList;

        private String readAccountAccountStateListByAccountStateCode;

        private String deleteAccountAccountStateListByAccountIdAndAccountStateCode;

        private String deleteAccountAccountStateListByAccountId;

        private String deleteAccountAccountStateListByAccountStateCode;
    }
}
