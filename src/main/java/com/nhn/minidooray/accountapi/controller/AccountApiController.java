package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.config.ApiMessageProperties;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private final AccountService accountService;
    private final ApiMessageProperties apiMessageProperties;


    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account.create-account}")
    public ResultResponse<Void> createAccount(@RequestBody @Valid AccountCreateRequest request,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.create(request);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.CREATED.value())
                        .resultMessage(apiMessageProperties.getCreateSuccMessage())
                        .build())
                .build();
    }

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account}")
    public ResultResponse<Void> updateAccount(@PathVariable String id,
                                              @RequestBody @Valid AccountUpdateRequest request,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        return accountService.update(id, request);
    }


    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account-name}")
    public ResultResponse<Void> updateAccountName(@PathVariable String id,
                                                  @RequestBody @Valid AccountUpdateRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.updateName(id, request);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getUpdateSuccMessage())
                        .build())
                .build();
    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account-password}")
    public ResultResponse<CommonResponse> updateAccountPasswordById(@PathVariable String id,
                                                                    @RequestBody AccountUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.updatePassword(id, request);
        return ResultResponse.<CommonResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getUpdateSuccMessage())
                        .build())
                .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-last-login-at}")
    public ResultResponse<Void> updateLastLoginAtById(@PathVariable String id) {
        accountService.updateByLastLoginAt(id);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getUpdateSuccMessage())
                        .build())
                .build();

    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-list}")
    public ResultResponse<Page<AccountResponse>> getAll(Pageable pageable) {
        Page<AccountResponse> result = accountService.getAll(pageable);
        return ResultResponse.<Page<AccountResponse>>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage(apiMessageProperties.getGetSuccMessage())
                        .build())
                .result(List.of(result))
                .build();
    }


    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-id}")
    public ResultResponse<AccountResponse> getAccount(@PathVariable("id") String id) {

        AccountResponse account = accountService.get(id);

        return ResultResponse.<AccountResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage("All account-account-states by account ID "
                                + apiMessageProperties.getGetSuccMessage())
                        .build())
                .result(List.of(account))
                .build();
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-email}")
    public ResultResponse<AccountResponse> getAccountByEmail(@PathVariable("email") String email) {

        AccountResponse account = null;
        try {
            account = accountService.findByEmail(email);
        } catch (NotFoundException e) {
            log.info("Not Found getAccountByEmail : {}", e.getMessage(), e);
        }

        return ResultResponse.<AccountResponse>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(HttpStatus.OK.value())
                        .resultMessage("All account-account-states by account ID "
                                + apiMessageProperties.getGetSuccMessage())
                        .build())
                .result(List.of(account))
                .build();

    }


}
