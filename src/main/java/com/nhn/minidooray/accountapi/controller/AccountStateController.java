package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// TODO MESSAGE SOURCE로 메세지 받아오게 해야함.

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountStateController {

    private final AccountStateService accountStateService;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-state}")
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
                    .resultMessage("Account state created successfully").build())
            .result(Collections.singletonList(createdAccountState)).build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.update-state}")
    public ResultResponse<AccountStateDto> updateAccountState(
        @RequestBody @Valid AccountStateCreateRequest accountStateCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        AccountStateDto updatedAccountState = accountStateService.update(accountStateCreateRequest);

        return ResultResponse.<AccountStateDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state updated successfully").build())
            .result(Collections.singletonList(updatedAccountState)).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-state-by-code}")
    public ResultResponse<AccountStateDto> readAccountStateByCode(
        @PathVariable("code") String code) {
        AccountStateDto accountStateDto = accountStateService.findByCode(code);

        return ResultResponse.<AccountStateDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state found").build())
            .result(Collections.singletonList(accountStateDto)).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-state-list}")
    public ResultResponse<List<AccountStateDto>> readAccountStateList() {
        List<AccountStateDto> accountStateDtoList = accountStateService.findAll();

        return ResultResponse.<List<AccountStateDto>>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account state list found").build())
            .result(Collections.singletonList(accountStateDtoList)).build();
    }
    // TODO AccountState 삭제시 존재하지 않는 데이터라고 나옴. 근데 삭제는 되어있음.
    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.delete-state-by-code}")
    public ResultResponse<Void> deleteAccountStateByCode(@PathVariable("code") String code) {
        accountStateService.deleteByCode(code);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultMessage("Account state deleted successfully").build()).build();
    }

    @DeleteMapping("${com.nhn.minidooray.accountapi.requestmapping.delete-all-state}")
    public ResultResponse<Void> deleteAllAccountStates() {
        accountStateService.deleteAll();

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true)
                .resultCode(HttpStatus.NO_CONTENT.value())
                .resultMessage("All account states deleted successfully").build()).build();
    }
}
