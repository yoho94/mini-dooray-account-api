package com.nhn.minidooray.accountapi.exception;

import org.springframework.http.HttpStatus;

public class AccountWithStateNotFoundException extends ApiException{
    public AccountWithStateNotFoundException() {
        super("No account found with the specified state.",HttpStatus.NOT_FOUND);
    }
}
