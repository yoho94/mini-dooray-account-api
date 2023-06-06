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
        String code = accountStateCreateRequest.getCode();
        if (accountStateRepository.existsById(code)) {
            throw new DataAlreadyExistsException(code);
        }

        AccountStateDto dto = convertToDto(accountStateCreateRequest);
        AccountStateEntity entity = convertToEntity(dto);

        return convertToDto(accountStateRepository.save(entity));
    }

    @Override
    public AccountStateDto update(AccountStateCreateRequest accountStateCreateRequest) {
        String code = accountStateCreateRequest.getCode();

        AccountStateEntity entity = getAccountStateEntityByCode(code);

        entity.setName(accountStateCreateRequest.getName());
        entity.setCreateAt(LocalDateTime.now());

        AccountStateEntity updatedEntity = accountStateRepository.save(entity);
        return convertToDto(updatedEntity);
    }

    @Override
    public List<AccountStateDto> findAll() {
        return accountStateRepository
            .findAll()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public AccountStateDto findByCode(String code) {
        AccountStateEntity entity = getAccountStateEntityByCode(code);

        return convertToDto(entity);
    }

    @Override
    public void delete(AccountStateDto accountStateDto) {
        String code =accountStateDto.getCode();

        if (!accountStateRepository.existsById(code)) {
            throw new DataNotFoundException(code, accountStateDto.getName());
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

    private AccountStateEntity getAccountStateEntityByCode(String code) {
        return accountStateRepository.findById(code)
            .orElseThrow(() -> new DataNotFoundException(code));
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
