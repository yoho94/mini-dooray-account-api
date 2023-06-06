package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountNameRequest;
import com.nhn.minidooray.accountapi.domain.request.ModifyAccountPasswordRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.exception.ApiException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.util.Collections;
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

// TODO MESSAGE SOURCE로 메세지 받아오게 해야함.
@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private final AccountService accountService;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-account}")
    public ResultResponse<Void> createAccount(
        @RequestBody @Valid AccountCreateRequest accountCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.save(accountCreateRequest);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true)
                .resultCode(HttpStatus.CREATED.value())
                .resultMessage("${com.nhn.minidooray.accountapi.message.create-succ-message}")
                .build()).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-account-by-id}")
    public ResultResponse<AccountDto> readAccountByID(@PathVariable("id") String id) {
        AccountDto account;

        try {
            account = accountService.findById(id);
        } catch (DataNotFoundException e) {
            throw new ApiException(e.getMessage(), e.getStatus());
        }

        return ResultResponse.<AccountDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(HttpStatus.OK.value())
                    .resultMessage("Account found").build()).result(Collections.singletonList(account))
            .build();

    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-account-by-email}")
    public ResultResponse<AccountDto> readAccountByEmail(@PathVariable("email") String email) {
        AccountDto account;

        try {
            account = accountService.findByEmail(email);
        } catch (DataNotFoundException e) {
            throw new ApiException(e.getMessage(), e.getStatus());
        }

        return ResultResponse.<AccountDto>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                    .resultMessage("Account found").build()).result(Collections.singletonList(account))
            .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.update-account}")
    public ResultResponse<Void> updateAccountLastLoginAt(@PathVariable String id) {
        accountService.updateByLastLoginAt(id);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200).resultMessage("성공")
                .build()).build();
    }

    // createAccountAccountState -> updateAccountAccountStateById
    // PostMapping("/status ") -> PostMapping("/create/status/id")
    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-account-state-by-id}")
    public ResultResponse<Void> createAccountAccountStateById(
        @RequestBody @Valid AccountAccountCreateRequest accountCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        accountService.updateStatusById(accountCreateRequest.getIdOrEmail(),
            accountCreateRequest.getAccountStateCode());

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(201).resultMessage("성공")
                .build()).build();
    }

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-account-state-by-email}")
    public ResultResponse<Void> createAccountAccountStateByEmail(
        @RequestBody @Valid AccountAccountCreateRequest accountCreateRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        accountService.updateStatusByEmail(accountCreateRequest.getIdOrEmail(),
            accountCreateRequest.getAccountStateCode());

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(201).resultMessage("성공")
                .build()).build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.update-account-name-by-id}")
    public ResultResponse<Void> updateNameById(
        @RequestBody @Valid ModifyAccountNameRequest modifyAccountNameRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.updateNameById(modifyAccountNameRequest);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account name successfully updated").build()).build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.update-account-name-by-email}")
    public ResultResponse<Void> updateNameByEmail(
        @RequestBody @Valid ModifyAccountNameRequest modifyAccountNameRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.updateNameByEmail(modifyAccountNameRequest);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account name successfully updated").build()).build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.update-account-password-by-id}")
    public ResultResponse<Void> updatePasswordById(
        @RequestBody @Valid ModifyAccountPasswordRequest modifyAccountPasswordRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        accountService.updatePasswordById(modifyAccountPasswordRequest);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account password successfully updated").build()).build();

    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.update-account-password-by-email}")
    public ResultResponse<Void> updatePasswordByEmail(
        @RequestBody @Valid ModifyAccountPasswordRequest modifyAccountPasswordRequest,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }
        accountService.updatePasswordByEmail(modifyAccountPasswordRequest);

        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account password successfully updated").build()).build();

    }

    /**
     * TODO totalCount가 항상 1이 나오는 문제있음.
     */
    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-account-list}")
    public ResultResponse<List<AccountDto>> readAccounts() {

        List<AccountDto> accounts = accountService.findAll();

        return ResultResponse.<List<AccountDto>>builder().header(
                ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                    .resultMessage("Accounts found").build())
            .result(Collections.singletonList(accounts)).build();

    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.deact-account-by-id}")
    public ResultResponse<Void> deactivationAccountById(@PathVariable String id) {
        accountService.deactivationById(id);
        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account deactivated").build()).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.deact-account-by-email}")
    public ResultResponse<Void> deactivationAccountByEmail(@PathVariable String email) {
        accountService.deactivationByEmail(email);
        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account deactivated").build()).build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.deact-accounts-by-all}")
    public ResultResponse<Void> deactivationAllAccountByList() {
        List<AccountDto> accounts = accountService.findAll();
        accountService.deactivationAllByAccounts(accounts);
        return ResultResponse.<Void>builder().header(
            ResultResponse.Header.builder().isSuccessful(true).resultCode(200)
                .resultMessage("Account deactivated").build()).build();
    }

}
