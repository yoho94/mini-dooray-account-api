package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.request.AccountStateCreateRequest;
import com.nhn.minidooray.accountapi.domain.response.AccountStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
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
    public String create(AccountStateCreateRequest accountStateCreateRequest) {
        String code = accountStateCreateRequest.getCode();
        if (accountStateRepository.existsById( code )) {
            throw new DataAlreadyExistsException( code );
        }
        return accountStateRepository.save( AccountStateEntity.builder()
                .code( accountStateCreateRequest.getCode() )
                .name( accountStateCreateRequest.getName() )
                .createAt( LocalDateTime.now() )
                .build() )
            .getCode();

    }


    @Override
    public List<AccountStateResponse> getAll() {
        return accountStateRepository
            .findAll()
            .stream()
            .map( (entity) ->
                AccountStateResponse.builder()
                    .code( entity.getCode() )
                    .name( entity.getName() )
                    .createAt( entity.getCreateAt() )
                    .build()
            )
            .collect( Collectors.toList() );
    }

    @Override
    public String find(String code) {
        return accountStateRepository.findById( code )
            .orElseThrow( () -> new NotFoundException( code ) ).getCode();

    }

    @Override
    public AccountStateResponse get(String code) {
        AccountStateEntity state = accountStateRepository.findById( code )
            .orElseThrow( () -> new NotFoundException( code ) );

        return AccountStateResponse.builder()
            .name( state.getName() )
            .code( state.getCode() )
            .createAt( state.getCreateAt() )
            .build();

    }

    @Override
    public void delete(String code) {
        if (!accountStateRepository.existsById( code )) {
            throw new NotFoundException( code );
        }
        accountStateRepository.deleteById( code );
    }

    @Override
    public void deleteAll() {
        accountStateRepository.deleteAll();
    }


}
