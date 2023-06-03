package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.service.AccountApiService;
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

    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.create-account}")
    public ResultResponse<Void> createAccount(@RequestBody @Valid AccountCreateRequest accountCreateRequest,
                                              BindingResult bindingResult) {


        return ResultResponse.<Void>builder()
                .build();
    }
//    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.read-accounts-by-id}?id={id}")
//    public ResultResponse<Void> readAccountsByID(@PathVariable("id") String id) {
//
//
//    }

}
