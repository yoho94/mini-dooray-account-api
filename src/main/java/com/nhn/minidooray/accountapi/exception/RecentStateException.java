package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class RecentStateException extends ApiException {

    private final String id;

    public RecentStateException(String id) {
        super("Recent state not found for account: " + id, HttpStatus.NOT_FOUND);
        this.id = id;
    }
}
