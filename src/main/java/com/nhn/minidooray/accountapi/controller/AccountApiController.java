package com.nhn.minidooray.accountapi.controller;

import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonResponse;
import com.nhn.minidooray.accountapi.domain.response.ResultResponse;
import com.nhn.minidooray.accountapi.exception.ValidationFailedException;
import com.nhn.minidooray.accountapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${com.nhn.minidooray.accountapi.requestmapping.prefix}")
public class AccountApiController {

    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("${com.nhn.minidooray.accountapi.requestmapping.account.create-account}")
    public ResultResponse<Void> createAccount(@RequestBody @Valid AccountCreateRequest request,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.create(request);
        return ResultResponse.created(null);


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
        return ResultResponse.updated(null);

    }

    @PutMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-account-password}")
    public ResultResponse<CommonResponse> updateAccountPasswordById(@PathVariable String id,
                                                                    @RequestBody AccountUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        accountService.updatePassword(id, request);
        return ResultResponse.updated(null);
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.update-last-login-at}")
    public ResultResponse<Void> updateLastLoginAtById(@PathVariable String id) {
        accountService.updateByLastLoginAt(id);
        return ResultResponse.updated(null);

    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-list}")
    public ResultResponse<Page<AccountResponse>> getAll(Pageable pageable) {
        Page<AccountResponse> result = accountService.getAll(pageable);
        return ResultResponse.fetched(Collections.singletonList(result));
    }


    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-id}")
    public ResultResponse<AccountResponse> getAccount(@PathVariable("id") String id) {

        AccountResponse account = accountService.get(id);

        return ResultResponse.fetched(Collections.singletonList(account));
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-accounts-by-id}")
    public ResultResponse<AccountResponse> getAccountsById(@Param("id") String[] ids) {

        List<AccountResponse> account = accountService.getAccountsById(ids);

        return ResultResponse.fetched(account);
    }

    @GetMapping("${com.nhn.minidooray.accountapi.requestmapping.account.read-account-by-email}")
    public ResultResponse<AccountResponse> getAccountByEmail(@PathVariable("email") String email) {

        AccountResponse account = accountService.findByEmail(email);

        return ResultResponse.fetched(Collections.singletonList(account));

    }


}
