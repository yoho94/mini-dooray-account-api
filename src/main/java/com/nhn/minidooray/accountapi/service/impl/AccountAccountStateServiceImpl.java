package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountAccountStateService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountAccountStateServiceImpl implements AccountAccountStateService {

    private final AccountAccountStateRepository accountAccountStateRepository;
    private final AccountRepository accountRepository;


    @Override
    @Transactional
    public void create(String accountId, String stateCode) {
        if (!IdOrEmailUtills.checkId( accountId )) {
            throw new InvalidIdFormatException( accountId );
        }
        AccountEntity account = accountRepository.findById( accountId )
            .orElseThrow( () -> new NotFoundException( accountId ) );


        AccountAccountStateEntity accountAccountStateEntity = AccountAccountStateEntity.builder()
            .account( account )
            .pk( Pk.builder().accountStateCode(stateCode ).accountId( account.getId() )
                .changeAt( LocalDateTime.now() ).build() )
            .build();

        accountAccountStateRepository.save( accountAccountStateEntity );
    }

    @Override
    @Transactional
    public Page<CommonAccountWithStateResponse> getByAccount(String accountId, Pageable pageable) {
        Page<AccountAccountStateEntity> entities = accountAccountStateRepository.findAllByAccount_IdOrderByPk_ChangeAt(accountId, pageable);
        if (!IdOrEmailUtills.checkId( accountId )) {
            throw new InvalidIdFormatException( accountId );
        }
        if (entities.isEmpty()) {
            throw new NotFoundException(accountId);
        }

        return entities.map(entity -> CommonAccountWithStateResponse.builder()
            .stateCode( entity.getPk().getAccountStateCode() )
            .accountId( entity.getPk().getAccountId() )
            .changeAt( entity.getPk().getChangeAt() )
            .build());
    }


    @Override
    public void deleteAccountStateById(Pk pk) {
        if (!accountAccountStateRepository.existsById( pk )) {
            throw new NotFoundException(pk.toString());
        }
        accountAccountStateRepository.deleteById( pk );


    }


}
