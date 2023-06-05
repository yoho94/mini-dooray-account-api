package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.request.AccountAccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.service.AccountApiService;
import com.nhn.minidooray.accountapi.service.AccountDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private AccountApiService accountApiService;
    private final AccountDetailService accountDetailService;
    private final AccountAccountStateService accountAccountStateService;

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-account}")
    public ResultResponse<Void> createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest) {
        accountDetailService.save(accountCreateRequest);
        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(201)
                        .resultMessage("Account successfully created")
                        .build())
                .build();
    }

    @GetMapping("/{id}")
    public ResultResponse<AccountDto> readAccountsByID(@PathVariable("id") String id) {
        Optional<AccountDto> account = accountDetailService.findById(id);

        if (account.isPresent()) {
            return ResultResponse.<AccountDto>builder()
                    .header(ResultResponse.Header.builder()
                            .isSuccessful(true)
                            .resultCode(200)
                            .resultMessage("Account found")
                            .build())
                    .result(Collections.singletonList(account.get()))
                    .build();
        } else {
            return ResultResponse.<AccountDto>builder()
                    .header(ResultResponse.Header.builder()
                            .isSuccessful(false)
                            .resultCode(404)
                            .resultMessage("Account not found")
                            .build())
                    .build();
        }
    }

    @GetMapping("/email/{email}")
    public ResultResponse<AccountDto> readAccountsByEmail(@PathVariable("email") String email) {
        Optional<AccountDto> account = accountDetailService.findByEmail(email);

        if (account.isPresent()) {
            return ResultResponse.<AccountDto>builder()
                    .header(ResultResponse.Header.builder()
                            .isSuccessful(true)
                            .resultCode(200)
                            .resultMessage("Account found")
                            .build())
                    .result(Collections.singletonList(account.get()))
                    .build();
        } else {
            return ResultResponse.<AccountDto>builder()
                    .header(ResultResponse.Header.builder()
                            .isSuccessful(false)
                            .resultCode(404)
                            .resultMessage("Account not found")
                            .build())
                    .build();
        }
    }

    @GetMapping("/login/{id}")
    public ResultResponse<Void> updateAccountLastLoginAt(@PathVariable String id) {
        accountDetailService.updateByLastLoginAt(id);

        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(200)
                        .resultMessage("성공")
                        .build())
                .build();
    }

    // TODO bindingResult 에러 처리 및 Valid 처리
    @PostMapping("/status")
    public ResultResponse<Void> createAccountAccountState(@RequestBody @Valid AccountAccountCreateRequest accountCreateRequest,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO thr new ...
        }
        accountDetailService.updateStatusById(accountCreateRequest.getAccountId(), accountCreateRequest.getAccountStateCode());


        return ResultResponse.<Void>builder()
                .header(ResultResponse.Header.builder()
                        .isSuccessful(true)
                        .resultCode(201)
                        .resultMessage("성공")
                        .build())
                .build();
    }
//    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-accounts-by-id}?id={id}")
//    public ResultResponse<Void> readAccountsByID(@PathVariable("id") String id) {
//
//
//    }

}
