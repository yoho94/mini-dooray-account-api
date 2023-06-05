package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataNotFoundException extends ApiException {

    private final String code;
    private final String id;

    public DataNotFoundException(String id, String code) {
        super("Data not found with id: " + id + " and code: " + code, HttpStatus.NOT_FOUND);
        this.code = code;
        this.id = id;
    }

    public DataNotFoundException(String id) {
        super("Data not found with id: " + id, HttpStatus.NOT_FOUND);
        this.id = id;
        this.code = null;
    }

}
