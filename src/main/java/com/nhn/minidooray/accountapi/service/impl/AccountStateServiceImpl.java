package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountStateServiceImpl implements AccountStateService {

    private final AccountStateRepository accountStateRepository;

    @Override
    public AccountStateDto save(AccountStateCreateRequest accountStateCreateRequest) {
        if (accountStateRepository.existsById(accountStateCreateRequest.getCode())) {

            throw new DataAlreadyExistsException(accountStateCreateRequest.getCode());
        }
        return convertToDto(
            accountStateRepository.save(convertToEntity(convertToDto(accountStateCreateRequest))));
    }

    @Override
    public AccountStateDto update(AccountStateCreateRequest accountStateCreateRequest) {
        AccountStateEntity entity = accountStateRepository.findById(
                accountStateCreateRequest.getCode())
            .orElseThrow(() -> new DataNotFoundException(accountStateCreateRequest.getCode()));
        entity.setName(accountStateCreateRequest.getName());
        entity.setCreateAt(LocalDateTime.now());
        return convertToDto(accountStateRepository.save(entity));
    }

    @Override
    public List<AccountStateDto> findAll() {
        return accountStateRepository.findAll().stream().map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public AccountStateDto findByCode(String code) {

        return accountStateRepository.findById(code).map(this::convertToDto)
            .orElseThrow(() -> new DataNotFoundException(code));

    }

    @Override
    public void delete(AccountStateDto accountStateDto) {
        if (!accountStateRepository.existsById(accountStateDto.getCode())) {
            throw new DataNotFoundException(accountStateDto.getCode(), accountStateDto.getName());

        }
        accountStateRepository.delete(convertToEntity(accountStateDto));

    }

    @Override
    public void deleteByCode(String code) {
        if (!accountStateRepository.existsById(code)) {
            throw new DataNotFoundException(code);

        }
        accountStateRepository.deleteById(code);



    }

    @Override
    public void deleteAll() {
        accountStateRepository.deleteAll();
    }

    private AccountStateDto convertToDto(AccountStateEntity accountStateEntity) {
        return AccountStateDto.builder()
            .code(accountStateEntity.getCode())
            .name(accountStateEntity.getName())
            .createAt(accountStateEntity.getCreateAt())
            .build();
    }

    private AccountStateDto convertToDto(AccountStateCreateRequest accountStateCreateRequest) {
        return AccountStateDto.builder()
            .code(accountStateCreateRequest.getCode())
            .name(accountStateCreateRequest.getName())
            .createAt(LocalDateTime.now())
            .build();
    }

    private AccountStateEntity convertToEntity(AccountStateDto accountStateDto) {
        return AccountStateEntity.builder()
            .code(accountStateDto.getCode())
            .name(accountStateDto.getName())
            .createAt(accountStateDto.getCreateAt())
            .build();
    }
}
