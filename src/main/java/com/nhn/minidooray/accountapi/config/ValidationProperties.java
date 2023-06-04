package com.nhn.minidooray.accountapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.nhn.minidooray.accountapi.validation")
@Getter
@Setter
public class ValidationProperties {
  String createIdSizeValidation;

}
