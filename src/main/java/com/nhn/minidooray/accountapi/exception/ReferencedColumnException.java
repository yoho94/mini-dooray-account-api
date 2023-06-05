package com.nhn.minidooray.accountapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReferencedColumnException extends ApiException{
 private String referencedColumn;
 public ReferencedColumnException(String referencedColumn) {
     super("Referenced column not found: " + referencedColumn, HttpStatus.NOT_FOUND);
     this.referencedColumn = referencedColumn;
 }

}
