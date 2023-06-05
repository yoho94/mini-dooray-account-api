package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.dto.AccountDto;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.service.AccountApiService;
import com.nhn.minidooray.accountapi.service.AccountDetailService;
import com.nhn.minidooray.accountapi.service.AccountService;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private AccountApiService accountApiService;
    private final AccountDetailService accountDetailService;

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
//    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-accounts-by-id}?id={id}")
//    public ResultResponse<Void> readAccountsByID(@PathVariable("id") String id) {
//
//
//    }

}
