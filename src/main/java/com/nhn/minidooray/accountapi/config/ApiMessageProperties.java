package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi")
@Getter
@Setter
public class ApiMessageProperties {

    private String prefix;

    private String createSuccMessage;
    private String updateSuccMessage;

    private String deactSuccMessage;

    private String deleteSuccMessage;

    private String getSuccMessage;


}
