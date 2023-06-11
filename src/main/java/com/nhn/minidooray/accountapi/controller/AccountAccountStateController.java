package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountAccountStateController {

    private final AccountAccountStateService accountAccountStateService;
    private final ApiMessageProperties messageProperties;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.create}")
    public ResultResponse<Void> createAccountAccountState(
        @RequestBody @Valid AccountAccountCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException( bindingResult );
        }
        accountAccountStateService.create( request.getAccountId(), request.getAccountStateCode() );

        return ResultResponse.<Void>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.CREATED.value() )
                .resultMessage( "AccountAccountState " + messageProperties.getCreateSuccMessage() )
                .build() )
            .build();
    }


    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.read-list-by-account-id}")
    public ResultResponse<Page<CommonAccountWithStateResponse>> getAccountWithStateByAccount(
        @PathVariable String accountId, Pageable pageable) {
        Page<CommonAccountWithStateResponse> accounts = accountAccountStateService.getByAccount(
            accountId, pageable );
        return ResultResponse.<Page<CommonAccountWithStateResponse>>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( "All account-account-states by account ID "
                    + messageProperties.getGetSuccMessage() )
                .build() )
            .result( List.of( accounts ) )
            .build();
    }


}
