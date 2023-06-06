package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.requestmapping")
@Getter
@Setter
public class RequestMappingProperties {

    private String prefix;

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
    private String readAccountStateCurrent;
    private String readAccountStateList;
    private String idCheck;
}
