package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO MESSAGE SOURCE로 메세지 받아오게 해야함.
@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountAccountStateController {
    private final AccountAccountStateService accountAccountStateService;


}
