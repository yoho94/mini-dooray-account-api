package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginFailException extends ApiException {

    private static final String SUFFIX = " is login fail";

    public LoginFailException(String target) {
        super( target + SUFFIX, HttpStatus.UNAUTHORIZED.value() );

    }


}
