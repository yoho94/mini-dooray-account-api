package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountStateResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountStateController {

    private final AccountStateService accountStateService;
    private final ApiMessageProperties messageProperties;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.create}")
    public ResultResponse<CommonResponse> createAccountState(
        @RequestBody @Valid AccountStateCreateRequest accountStateCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException( bindingResult );
        }
        String createdAccountState = accountStateService.create( accountStateCreateRequest );

        return ResultResponse.<CommonResponse>builder().header(
                ResultResponse.Header.builder().isSuccessful( true )
                    .resultCode( HttpStatus.CREATED.value() )
                    .resultMessage( "Account state " + messageProperties.getCreateSuccMessage() )
                    .build() )
            .result( List.of( CommonResponse.builder().id( createdAccountState ).build() ))
            .build();
    }


    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.read-by-code}")
    public ResultResponse<CommonResponse> readAccountStateByCode(
        @PathVariable("code") String code) {

        String result = accountStateService.find( code );
        return ResultResponse.<CommonResponse>builder().header(
                ResultResponse.Header.builder().isSuccessful( true ).resultCode( HttpStatus.OK.value() )
                    .resultMessage( "Account state " + messageProperties.getGetSuccMessage() ).build() )
            .result( List.of( CommonResponse.builder().id( result ).build()))
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.read-list}")
    public ResultResponse<List<AccountStateResponse>> readAccountStateList() {
        List<AccountStateResponse> accountStateResponseList = accountStateService.getAll();

        return ResultResponse.<List<AccountStateResponse>>builder().header(
                ResultResponse.Header.builder().isSuccessful( true ).resultCode( HttpStatus.OK.value() )
                    .resultMessage( "Account state list " + messageProperties.getGetSuccMessage() )
                    .build() )
            .result( Collections.singletonList( accountStateResponseList ) ).build();
    }

    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.delete-by-code}")
    public ResultResponse<Void> deleteAccountStateByCode(@PathVariable("code") String code) {
        accountStateService.delete( code );

        return ResultResponse.<Void>builder().header(
                ResultResponse.Header.builder().isSuccessful( true )
                    .resultCode( HttpStatus.NO_CONTENT.value() )
                    .resultMessage( "Account state " + messageProperties.getDeleteSuccMessage() )
                    .build() )
            .build();
    }

}
