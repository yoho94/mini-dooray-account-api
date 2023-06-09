package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.response.AccountWithStateByAccountResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountAccountStateServiceImpl implements AccountAccountStateService {

    private final AccountAccountStateRepository accountAccountStateRepository;
    private final AccountRepository accountRepository;
    private final AccountStateRepository accountStateRepository;


    @Override
    @Transactional
    public void create(String accountId, String stateCode) {
        if (!IdOrEmailUtills.checkId( accountId )) {
            throw new InvalidIdFormatException( accountId );
        }
        AccountEntity account = accountRepository.findById( accountId )
            .orElseThrow( () -> new NotFoundException( accountId ) );

        AccountStateEntity state = accountStateRepository.findById( stateCode )
            .orElseThrow( () -> new NotFoundException( stateCode ) );

        AccountAccountStateEntity accountAccountStateEntity = AccountAccountStateEntity.builder()
            .accountState( state )
            .account( account )
            .pk( Pk.builder().accountStateCode( state.getCode() ).accountId( account.getId() )
                .changeAt( LocalDateTime.now() ).build() )
            .build();

        accountAccountStateRepository.save( accountAccountStateEntity );
    }

    @Override
    @Transactional
    public AccountWithStateByAccountResponse getByAccount(String accountId) {
        List<CommonAccountWithStateResponse> lists = accountAccountStateRepository.findAllByAccount_IdOrderByPk_ChangeAt(
                accountId )
            .stream()
            .map( (entity) -> CommonAccountWithStateResponse.builder()
                .stateCode( entity.getPk().getAccountStateCode() )
                .accountId( entity.getPk().getAccountId() )
                .changeAt( entity.getPk().getChangeAt() )
                .build() )
            .collect( Collectors.toList() );
        if (lists.isEmpty()) {
            throw new NotFoundException(accountId);
        }
        return AccountWithStateByAccountResponse.builder()
            .accountId( accountId )
            .changes( lists )
            .build();

    }

    @Override
    public List<CommonAccountWithStateResponse> getAllByAccountIdAndAccountStateCode(String accountId,
        String accountStateCode) {
        List<AccountAccountStateEntity> entities = accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(
            accountId, accountStateCode );

        if (entities.isEmpty()) {
            throw new NotFoundException(accountId+" , "+accountStateCode);
        }
        return entities.stream()
            .map( (entity) -> CommonAccountWithStateResponse.builder()
                .stateCode( entity.getPk().getAccountStateCode() )
                .accountId( entity.getPk().getAccountId() )
                .changeAt( entity.getPk().getChangeAt() )
                .build() )
            .collect( Collectors.toList() );
    }

    @Override
    public List<CommonAccountWithStateResponse> getAll() {

        return accountAccountStateRepository
            .findAll()
            .stream()
            .map( (entity) -> CommonAccountWithStateResponse.builder()
                .stateCode( entity.getPk().getAccountStateCode() )
                .accountId( entity.getPk().getAccountId() )
                .changeAt( entity.getPk().getChangeAt() )
                .build() )
            .collect( Collectors.toList() );
    }

    @Override
    public List<CommonAccountWithStateResponse> getByAccountStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> exists = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode );
        if (exists.isEmpty()) {
            throw new NotFoundException(accountStateCode);
        }
        return exists
            .stream()
            .map( (entity) -> CommonAccountWithStateResponse.builder()
                .stateCode( entity.getPk().getAccountStateCode() )
                .accountId( entity.getPk().getAccountId() )
                .changeAt( entity.getPk().getChangeAt() )
                .build() )
            .collect( Collectors.toList() );
    }

    @Override
    public void deleteAccountStateById(Pk pk) {
        if (!accountAccountStateRepository.existsById( pk )) {
            throw new NotFoundException(pk.toString());
        }
        accountAccountStateRepository.deleteById( pk );


    }

    @Override
    public void deleteAllByAccountIdAndAccountStateCode(String accountId, String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_IdAndAccountState_Code(
            accountId, accountStateCode );

        if (existed.isEmpty()) {
            throw new NotFoundException(accountId+" , "+accountStateCode);

        }
        accountAccountStateRepository.deleteAll( existed );
    }

    @Override
    public void deleteAllByAccountId(String accountId) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccount_IdOrderByPk_ChangeAt(
            accountId );

        if (existed.isEmpty()) {
            throw new NotFoundException(accountId);
        }
        accountAccountStateRepository.deleteAll( existed );
    }

    @Override
    public void deleteAllByStateCode(String accountStateCode) {
        List<AccountAccountStateEntity> existed = accountAccountStateRepository.findAllByAccountStateCode(
            accountStateCode );

        if (existed.isEmpty()) {
            throw new NotFoundException(accountStateCode);
        }

        accountAccountStateRepository.deleteAll( existed );

    }


}
