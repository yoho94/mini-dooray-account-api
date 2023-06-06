package com.nhn.minidooray.accountapi.advice;

import com.nhn.minidooray.accountapi.exception.AccountWithStateNotFoundException;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.LoginFailException;
import com.nhn.minidooray.accountapi.exception.RecentStateException;
import com.nhn.minidooray.accountapi.exception.ReferencedColumnException;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// todo error 처리
@RestControllerAdvice
public class ApiAdvice {

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<String> handleValidationFailedException(ValidationFailedException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException ex) {
        String errorMessage = String.format(ex.getMessage(), ex.getCode(), ex.getId());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<String> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        String errorMessage = String.format(ex.getMessage(), ex.getId());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(LoginFailException.class)
    public ResponseEntity<String> handleLoginFailException(LoginFailException ex) {
        String errorMessage = String.format(ex.getMessage(), ex.getId());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(RecentStateException.class)
    public ResponseEntity<String> handleRecentStateException(RecentStateException ex) {
        String errorMessage = String.format(ex.getMessage(), ex.getId());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }
    @ExceptionHandler(AccountWithStateNotFoundException.class)
    public ResponseEntity<String> handleAccountWithStateNotFoundException(AccountWithStateNotFoundException ex) {
        String errorMessage = String.format(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }

    @ExceptionHandler(ReferencedColumnException.class)
    public ResponseEntity<String> handleReferencedColumnException(ReferencedColumnException ex) {
        String errorMessage = String.format(ex.getMessage(),ex.getReferencedColumn());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }
    @ExceptionHandler(InvalidEmailFormatException.class)
    public ResponseEntity<String> handleInvalidEmailFormatException(InvalidEmailFormatException ex) {
        String errorMessage = String.format(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }
    @ExceptionHandler(InvalidIdFormatException.class)
    public ResponseEntity<String> handleInvalidIdFormatException(InvalidIdFormatException ex) {
        String errorMessage = String.format(ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }
}
