package com.nhn.minidooray.accountapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.nhn.minidooray.accountapi.config")
public class MiniDoorayAccountApiApplication {

    public static void main(String[] args) {
        SpringApplication.run( MiniDoorayAccountApiApplication.class, args );
    }

}
