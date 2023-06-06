package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.MessageProperties;
import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountAccountStateController {
    private final AccountAccountStateService accountAccountStateService;
    private final MessageProperties messageProperties;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.create-account-account-state}")
    public ResultResponse<AccountAccountStateDto> createAccountAccountState(@RequestBody @Valid AccountAccountCreateRequest accountAccountCreateRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        AccountAccountStateDto result = accountAccountStateService.save(accountAccountCreateRequest);

        return ResultResponse.<AccountAccountStateDto>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.CREATED.value())
                .resultMessage("AccountAccountState "+messageProperties.getCreateSuccMessage())
                .build())
            .result(Collections.singletonList(result))
            .build();
    }
    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.read-account-account-state-list-by-account-id-and-account-state-code}")
    public ResultResponse<AccountAccountStateDto> readAllAccountAccountStateByAccountIdAndAccountStateCode(@PathVariable String accountId, @PathVariable String accountStateCode) {
        List<AccountAccountStateDto> results = accountAccountStateService.findAllByAccountIdAndAccountStateCode(accountId, accountStateCode);

        return ResultResponse.<AccountAccountStateDto>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("AccountAccountStates "+messageProperties.getGetSuccMessage())
                .build())
            .result(results)
            .build();
    }
    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.read-account-account-state-list}")
    public ResultResponse<List<AccountAccountStateDto>> readAllAccountAccountState() {
        List<AccountAccountStateDto> accountAccountStateDtos = accountAccountStateService.findAll();
        return ResultResponse.<List<AccountAccountStateDto>>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("All account-account-states "+messageProperties.getGetSuccMessage())
                .build())
            .result(Collections.singletonList(accountAccountStateDtos))
            .build();
    }
    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.read-account-account-state-list-by-account-state-code}")
    public ResultResponse<List<AccountAccountStateDto>> findAllByAccountStateCode(@PathVariable String accountStateCode) {
        List<AccountAccountStateDto> accountAccountStateDtos = accountAccountStateService.findAllByAccountStateCode(accountStateCode);
        return ResultResponse.<List<AccountAccountStateDto>>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("All account-account-states by account state code "+messageProperties.getGetSuccMessage())
                .build())
            .result(Collections.singletonList(accountAccountStateDtos))
            .build();
    }
    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.delete-account-account-state-list-by-account-id-and-account-state-code}")
    public ResultResponse<Void> deleteAllByAccountIdAndAccountStateCode(@PathVariable String accountId, @PathVariable String accountStateCode) {
        accountAccountStateService.deleteAllByAccountIdAndAccountStateCode(accountId, accountStateCode);
        return ResultResponse.<Void>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("All account-account-states by account ID and account state code "+messageProperties.getDeleteSuccMessage())
                .build())
            .build();
    }
    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.delete-account-account-state-list-by-account-id}")
    public ResultResponse<Void> deleteAllByAccountId(@PathVariable String accountId) {
        accountAccountStateService.deleteAllByAccountId(accountId);
        return ResultResponse.<Void>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("All account-account-states by account ID "+messageProperties.getDeleteSuccMessage())
                .build())
            .build();
    }
    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-account-state.delete-account-account-state-list-by-account-state-code}")
    public ResultResponse<Void> deleteAllByAccountStateCode(@PathVariable String accountStateCode) {
        accountAccountStateService.deleteAllByAccountStateCode(accountStateCode);
        return ResultResponse.<Void>builder()
            .header(ResultResponse.Header.builder()
                .isSuccessful(true)
                .resultCode(HttpStatus.OK.value())
                .resultMessage("All account-account-states by account state code "+messageProperties.getDeleteSuccMessage())
                .build())
            .build();
    }
}
