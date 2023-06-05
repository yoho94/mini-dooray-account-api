package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.DataNotFoundException;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountStateService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountStateServiceImpl implements AccountStateService {

    private final AccountStateRepository accountStateRepository;

    @Override
    public AccountStateDto save(AccountStateDto accountStateDto) {
        if (accountStateRepository.existsById(accountStateDto.getCode())) {

            throw new DataAlreadyExistsException(accountStateDto.getCode());
        }
        return convertToDto(accountStateRepository.save(convertToEntity(accountStateDto)));
    }

    @Override
    public AccountStateDto update(AccountStateDto accountStateDto) {
        if (accountStateRepository.existsById(accountStateDto.getCode())) {
            accountStateRepository.save(convertToEntity(accountStateDto));
        }
        throw new DataNotFoundException(accountStateDto.getCode(), accountStateDto.getName());
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
        if (accountStateRepository.existsById(accountStateDto.getCode())) {
            accountStateRepository.delete(convertToEntity(accountStateDto));
        }
        throw new DataNotFoundException(accountStateDto.getCode(), accountStateDto.getName());
    }

    @Override
    public void deleteByCode(String code) {
        if (accountStateRepository.existsById(code)) {
            accountStateRepository.deleteById(code);
        }
        throw new DataNotFoundException(code);


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

    private AccountStateEntity convertToEntity(AccountStateDto accountStateDto) {
        return AccountStateEntity.builder()
            .code(accountStateDto.getCode())
            .name(accountStateDto.getName())
            .createAt(accountStateDto.getCreateAt())
            .build();
    }
}
