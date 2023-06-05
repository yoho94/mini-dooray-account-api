package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LoginFailException extends ApiException {
    private final String id;
    public LoginFailException(String id) {
        super("ID does not exist, or incorrect ID or password was entered. :" + id, HttpStatus.UNAUTHORIZED);
        this.id = id;
    }


}
