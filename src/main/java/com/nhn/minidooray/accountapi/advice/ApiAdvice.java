package com.nhn.minidooray.accountapi.advice;

import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ApiException;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiAdvice {

    @ExceptionHandler(ApiException.class)
    public ResultResponse<Void> handleApiException(ApiException ex) {
        log.error( "handleApiException : {}", ex.getMessage(), ex );
        return ResultResponse.<Void>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( false )
                .resultCode( ex.getStatusCode() )
                .resultMessage( ex.getMessage() )
                .build() )
            .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResultResponse<Void> validException(ConstraintViolationException e) {
        log.error( "validException : {}", e.getMessage(), e );

        return ResultResponse.<Void>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( false )
                .resultCode( HttpStatus.BAD_REQUEST.value() )
                .resultMessage( e.getMessage() )
                .build() )
            .build();
    }
}
