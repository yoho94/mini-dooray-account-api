package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateType;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.AccountWithStateResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.RecentStateException;
import com.nhn.minidooray.accountapi.exception.InvalidRequestException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AccountDetailService imple은 AccountDetailDto에 AccountSerialDto를 임시로 넣어보았음. 잘 동작하면 다른 것들도 추가할 예정
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountAccountStateRepository accountAccountStateRepository;

    @Override
    @Transactional
    public void create(AccountCreateRequest accountCreateRequest) {
        if (!IdOrEmailUtills.checkId( accountCreateRequest.getId() )) {
            throw new InvalidIdFormatException( accountCreateRequest.getId() );
        }
        if (!IdOrEmailUtills.checkEmail( accountCreateRequest.getEmail() )) {
            throw new InvalidEmailFormatException( accountCreateRequest.getEmail() );
        }

        if (accountRepository.existsById( accountCreateRequest.getId() )) {
            throw new DataAlreadyExistsException( accountCreateRequest.getId() );
        }
        AccountEntity account = AccountEntity.builder().id( accountCreateRequest.getId() )
            .password( accountCreateRequest.getPassword() ).email( accountCreateRequest.getEmail() )
            .name( accountCreateRequest.getName() ).build();
        accountRepository.save( account );


        AccountAccountStateEntity accountState = AccountAccountStateEntity.builder()
            .account( account )
            .pk( Pk.builder().accountId( account.getId() ).accountStateCode( AccountStateType.REGISTER.getCode() )
                .changeAt( LocalDateTime.now() ).build() )
            .build();

        accountAccountStateRepository.save( accountState );
    }

    @Override
    public void updateName(String id, AccountUpdateRequest accountUpdateNameRequest) {
        if (!IdOrEmailUtills.checkId( id )) {
            throw new InvalidIdFormatException( id );
        }
        if (!AccountUpdateType.NAME_UPDATE.validate( accountUpdateNameRequest )) {
            throw new InvalidRequestException(id);
        }

        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        account.update( accountUpdateNameRequest );

    }

    @Override
    public void updatePassword(String id,
        AccountUpdateRequest accountUpdatePasswordRequest) {
        if (!IdOrEmailUtills.checkId( id )) {
            throw new InvalidIdFormatException( id );
        }
        if (!AccountUpdateType.PASSWORD_UPDATE.validate( accountUpdatePasswordRequest )) {
            throw new InvalidRequestException( id );
        }
        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        account.update( accountUpdatePasswordRequest );

      accountRepository.save( account );

    }
    @Override
    public AccountResponse findByEmail(String email) {
        if (!IdOrEmailUtills.checkEmail( email )) {
            throw new InvalidEmailFormatException( email );
        }
        AccountEntity entity = accountRepository.findByEmail( email )
            .orElseThrow( () -> new NotFoundException( email ) );

        return AccountResponse.builder()
            .id( entity.getId() )
            .email( entity.getEmail() )
            .name( entity.getName() )
            .lastLoginAt( entity.getLastLoginAt() )
            .createdAt( entity.getCreateAt() )
            .build();


    }

    @Override
    @Transactional
    public void updateByLastLoginAt(String id) {

        AccountEntity entity = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        entity.update( AccountUpdateRequest.builder()
            .lastLoginAt( LocalDateTime.now() )
            .build() );

        accountRepository.save( entity );

    }

    @Override
    public AccountResponse get(String id) {
        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        return AccountResponse.builder()
            .createdAt( account.getCreateAt() )
            .lastLoginAt( account.getLastLoginAt() )
            .email( account.getEmail() )
            .id( account.getId() )
            .name( account.getName() )
            .build();
    }

    @Override
    public Page<AccountResponse> getAll(Pageable pageable) {
        Page<AccountResponse> pages = accountRepository.findAll(pageable)
            .map(accountEntity -> AccountResponse.builder()
                .id(accountEntity.getId())
                .name(accountEntity.getName())
                .email(accountEntity.getEmail())
                .lastLoginAt(accountEntity.getLastLoginAt())
                .createdAt(accountEntity.getCreateAt())
                .build());

        if(pages.isEmpty()){
            throw new InvalidRequestException( pageable.toString() );
        }

        return pages;
    }



}
