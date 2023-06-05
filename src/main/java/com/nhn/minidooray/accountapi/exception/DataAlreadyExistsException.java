package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataAlreadyExistsException extends ApiException {

    private final String id;

    public DataAlreadyExistsException(String id) {
        super("Data already exists with id: " + id, HttpStatus.CONFLICT);
        this.id = id;
    }

}
