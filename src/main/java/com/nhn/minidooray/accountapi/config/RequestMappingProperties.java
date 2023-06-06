package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.requestmapping")
@Getter
@Setter
public class RequestMappingProperties {

    private String prefix;
    // ACCOUNT SERVICE
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

    // ACCOUNT STATE SERVICE
    private String createAccountState;

    private String updateAccountState;

    private String readAccountStateList;
    private String readAccountStateByCode;

    private String deleteAccountListStateByCode;
    private String deleteAccountListState;

    // ACCOUNT ACCOUNT STATE SERVICE

    private String createAccountAccountState;
    private String readAccountAccountStateListByAccountIdAndAccountStateCode;

    private String readAccountAccountStateList;

    private String readAccountAccountStateListByAccountStateCode;

    private String deleteAccountAccountStateListByAccountIdAndAccountStateCode;

    private String deleteAccountAccountStateListByAccountId;

    private String deleteAccountAccountStateListByAccountStateCode;
}
