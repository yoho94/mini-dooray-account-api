package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.MessageProperties;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
public class AccountStateController {

    private final AccountStateService accountStateService;
    private final MessageProperties messageProperties;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.create-account-state}")
    public ResultResponse<AccountStateDto> createAccountState(
        @RequestBody @Valid AccountStateCreateRequest accountStateCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        AccountStateDto createdAccountState = accountStateService.save(accountStateCreateRequest);

        return ResultResponse.<AccountStateDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true)
                    .resultCode(HttpStatus.CREATED.value())
                    .resultMessage("Account state " + messageProperties.getCreateSuccMessage()).build())
            .result(Collections.singletonList(createdAccountState)).build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.update-account-state}")
    public ResultResponse<AccountStateDto> updateAccountState(
        @RequestBody @Valid AccountStateCreateRequest accountStateCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        AccountStateDto updatedAccountState = accountStateService.update(accountStateCreateRequest);

        return ResultResponse.<AccountStateDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state " + messageProperties.getUpdateSuccMessage()).build())
            .result(Collections.singletonList(updatedAccountState)).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.read-account-state-by-code}")
    public ResultResponse<AccountStateDto> readAccountStateByCode(
        @PathVariable("code") String code) {
        AccountStateDto accountStateDto = accountStateService.findByCode(code);

        return ResultResponse.<AccountStateDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state " + messageProperties.getGetSuccMessage()).build())
            .result(Collections.singletonList(accountStateDto)).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.read-account-state-list}")
    public ResultResponse<List<AccountStateDto>> readAccountStateList() {
        List<AccountStateDto> accountStateDtoList = accountStateService.findAll();

        return ResultResponse.<List<AccountStateDto>>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state list " + messageProperties.getGetSuccMessage())
                    .build())
            .result(Collections.singletonList(accountStateDtoList)).build();
    }

    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.delete-account-list-state-by-code}")
    public ResultResponse<Void> deleteAccountStateByCode(@PathVariable("code") String code) {
        accountStateService.deleteByCode(code);

        return ResultResponse.<Void>builder().header(
                ResultResponse.Header.builder().isSuccessful(true)
                    .resultCode(HttpStatus.NO_CONTENT.value())
                    .resultMessage("Account state " + messageProperties.getDeleteSuccMessage()).build())
            .build();
    }

    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.account-state.delete-account-list-state}")
    public ResultResponse<Void> deleteAllAccountStates() {
        accountStateService.deleteAll();

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultMessage("All account states " + messageProperties.getDeleteSuccMessage())
                .build()).build();
    }
}
