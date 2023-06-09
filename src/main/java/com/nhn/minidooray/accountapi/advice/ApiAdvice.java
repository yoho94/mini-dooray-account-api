package com.nhn.minidooray.accountapi.advice;

import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiAdvice {
    @ExceptionHandler(ApiException.class)
    public ResultResponse<Void> handleApiException(ApiException ex) {
        return ResultResponse.<Void>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(false)
                .resultCode(ex.getStatusCode())
                .resultMessage(ex.getMessage())
                .build())
            .build();
    }
}
