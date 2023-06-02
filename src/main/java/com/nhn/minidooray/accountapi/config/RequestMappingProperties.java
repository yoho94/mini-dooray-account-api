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
    private String readAccount;
    private String readAccountList;
    private String updateAccount;
    private String deleteAccount;

    private String createAccountState;
    private String readAccountStateCurrent;
    private String readAccountStateList;

    private String idCheck;
}
