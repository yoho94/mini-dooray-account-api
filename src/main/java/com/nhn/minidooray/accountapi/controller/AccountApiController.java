package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.AccountWithStateResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private final AccountService accountService;
    private final ApiMessageProperties apiMessageProperties;


    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account.create-account}")
    public ResultResponse<CommonResponse> createAccount(@RequestBody @Valid AccountCreateRequest request,
        BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException( bindingResult );
        }

        String result = accountService.create( request );
        return ResultResponse.<CommonResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.CREATED.value() )
                .resultMessage( apiMessageProperties.getCreateSuccMessage() )
                .build() )
            .result( List.of( CommonResponse.builder().id( result ).build() ) )
            .build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account-name}")
    public ResultResponse<CommonResponse> updateAccountName(@PathVariable String id,
        @RequestBody @Valid AccountUpdateRequest request, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException( bindingResult );
        }

        String result = accountService.updateName( id, request );
        return ResultResponse.<CommonResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getUpdateSuccMessage() )
                .build() )
            .result( List.of( CommonResponse.builder().id( result ).build() ) )
            .build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account-password}")
    public ResultResponse<CommonResponse> updateAccountPasswordById(@PathVariable String id,
        @RequestBody AccountUpdateRequest request) {
        String result = accountService.updatePassword( id, request );
        return ResultResponse.<CommonResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getUpdateSuccMessage() )
                .build() )
            .result( List.of( CommonResponse.builder().id( result ).build() ) )
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-last-login-at}")
    public ResultResponse<Void> updateLastLoginAtById(@PathVariable String id) {
        accountService.updateByLastLoginAt( id );
        return ResultResponse.<Void>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getUpdateSuccMessage() )
                .build() )
            .build();

    }


    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-last-change-at}")
    public ResultResponse<AccountWithStateResponse> getTopAccountWithChangeAt(
        @PathVariable String id) {
        AccountWithStateResponse result = accountService.getTopByIdWithChangeAt( id );
        return ResultResponse.<AccountWithStateResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getGetSuccMessage() )
                .build() )
            .result( List.of( result ) )
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-id}")
    public ResultResponse<CommonResponse> findById(@PathVariable String id) {
        String result = accountService.find( id );
        return ResultResponse.<CommonResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getGetSuccMessage() )
                .build() )
            .result( List.of( CommonResponse.builder().id( result ).build() ) )
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-email}")
    public ResultResponse<CommonResponse> findByEmail(@PathVariable String email) {
        String result = accountService.findByEmail( email );
        return ResultResponse.<CommonResponse>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getGetSuccMessage() )
                .build() )
            .result( List.of( CommonResponse.builder().id( result ).build() ) )
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-list}")
    public ResultResponse<List<AccountResponse>> findAll() {
        List<AccountResponse> result = accountService.getAll();
        return ResultResponse.<List<AccountResponse>>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getGetSuccMessage() )
                .build() )
            .result( List.of( result ) )
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.deact-account}")
    public ResultResponse<Void> deactivationById(@PathVariable String id) {
        accountService.deactivationById( id );
        return ResultResponse.<Void>builder()
            .header( ResultResponse.Header.builder()
                .isSuccessful( true )
                .resultCode( HttpStatus.OK.value() )
                .resultMessage( apiMessageProperties.getDeactSuccMessage() )
                .build() )
            .build();
    }


}
