package com.nhn.minidooray.accountapi.service.impl;

import com.nhn.minidooray.accountapi.domain.enums.AccountStatus;
import com.nhn.minidooray.accountapi.domain.request.AccountCreateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;
import com.nhn.minidooray.accountapi.domain.request.AccountUpdateType;
import com.nhn.minidooray.accountapi.domain.response.AccountResponse;
import com.nhn.minidooray.accountapi.domain.response.AccountWithStateResponse;
import com.nhn.minidooray.accountapi.domain.response.CommonAccountWithStateResponse;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import com.nhn.minidooray.accountapi.exception.DataAlreadyExistsException;
import com.nhn.minidooray.accountapi.exception.NotFoundException;
import com.nhn.minidooray.accountapi.exception.InvalidEmailFormatException;
import com.nhn.minidooray.accountapi.exception.InvalidIdFormatException;
import com.nhn.minidooray.accountapi.exception.RecentStateException;
import com.nhn.minidooray.accountapi.exception.RequestInvalidException;
import com.nhn.minidooray.accountapi.repository.AccountAccountStateRepository;
import com.nhn.minidooray.accountapi.repository.AccountRepository;
import com.nhn.minidooray.accountapi.repository.AccountStateRepository;
import com.nhn.minidooray.accountapi.service.AccountService;
import com.nhn.minidooray.accountapi.util.IdOrEmailUtills;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    private final AccountStateRepository accountStateRepository;
    private final AccountAccountStateRepository accountAccountStateRepository;

    @Override
    @Transactional
    public String create(AccountCreateRequest accountCreateRequest) {
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

        AccountStateEntity state = accountStateRepository.findById(
            AccountStatus.REGISTERED.getStatusValue() ).orElseThrow(
            () -> new NotFoundException( AccountStatus.REGISTERED.getStatusValue() ) );

        AccountAccountStateEntity accountState = AccountAccountStateEntity.builder()
            .account( account ).accountState( state )
            .pk( Pk.builder().accountId( account.getId() ).accountStateCode( state.getCode() )
                .changeAt( LocalDateTime.now() ).build() )
            .build();

        accountAccountStateRepository.save( accountState );

        return account.getId();
    }


    @Override
    public String updateName(String id, AccountUpdateRequest accountUpdateNameRequest) {
        if (!IdOrEmailUtills.checkId( id )) {
            throw new InvalidIdFormatException( id );
        }
        if (!AccountUpdateType.NAME_UPDATE.validate( accountUpdateNameRequest )) {
            throw new RequestInvalidException( "올바르지 않은 요청입니다." );
        }

        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        account.update( accountUpdateNameRequest );

        return accountRepository.save( account ).getId();
    }


    @Override
    public String updatePassword(String id,
        AccountUpdateRequest accountUpdatePasswordRequest) {
        if (!IdOrEmailUtills.checkId( id )) {
            throw new InvalidIdFormatException( id );
        }
        if (!AccountUpdateType.PASSWORD_UPDATE.validate( accountUpdatePasswordRequest )) {
            throw new RequestInvalidException( "올바르지 않은 요청입니다." );
        }
        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        account.update( accountUpdatePasswordRequest );

        return accountRepository.save( account ).getId();

    }


    @Override
    @Transactional
    public AccountWithStateResponse getTopByIdWithChangeAt(String id) {
        AccountEntity existedAccount = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        AccountAccountStateEntity state = accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(
            id ).orElseThrow( () -> new RecentStateException( id ) );

        return AccountWithStateResponse.builder()
            .accountDetail( AccountResponse.
                builder().
                id( existedAccount.getId() ).
                name( existedAccount.getName() ).
                email( existedAccount.getEmail() ).
                lastLoginAt( existedAccount.getLastLoginAt() ).
                createdAt( existedAccount.getCreateAt() ).
                build() )
            .accountWithState( CommonAccountWithStateResponse
                .builder()
                .accountId( state.getPk().getAccountId() )
                .stateCode( state.getPk().getAccountStateCode() )
                .changeAt( state.getPk().getChangeAt() ).build() )
            .build();

    }


    @Override
    public String find(String id) {
        return accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) ).getId();

    }

    @Override
    public String findByEmail(String email) {
        if (!IdOrEmailUtills.checkEmail( email )) {
            throw new InvalidEmailFormatException( "InvalidEmailFormatException" );
        }
        return accountRepository.findByEmail( email )
            .orElseThrow( () -> new NotFoundException( email ) )
            .getId();


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
    public List<AccountResponse> getAll() {
        return accountRepository.findAll().stream()
            .map( accountEntity -> AccountResponse.builder()
                .id( accountEntity.getId() )
                .name( accountEntity.getName() )
                .email( accountEntity.getEmail() )
                .lastLoginAt( accountEntity.getLastLoginAt() )
                .createdAt( accountEntity.getCreateAt() )
                .build() ).collect( Collectors.toList() );
    }

    @Override
    @Transactional
    public void deactivationById(String id) {

        AccountEntity account = accountRepository.findById( id )
            .orElseThrow( () -> new NotFoundException( id ) );

        AccountStateEntity state = accountStateRepository.findById(
            AccountStatus.DEACTIVATED.getStatusValue() ).orElseThrow(
            () -> new NotFoundException( AccountStatus.DEACTIVATED.getStatusValue() )
        );

        AccountAccountStateEntity accountAccountStateEntity = AccountAccountStateEntity.builder()
            .account( account ).accountState( state )
            .pk( Pk.builder().accountId( account.getId() ).accountStateCode( state.getCode() )
                .changeAt( LocalDateTime.now() )
                .build() )
            .build();

        // 만약 최근 상태 코드가 탈퇴이고, 다시 비활성화 명령이 들어오면 throw
        AccountStateEntity lastStateCode = accountAccountStateRepository
            .findTopByAccount_IdOrderByPk_ChangeAtDesc( id )
            .orElseThrow( () -> new RecentStateException( id ) )
            .getAccountState();

        if (lastStateCode.getCode().equals( state.getCode() )) {
            throw new RecentStateException( id + "는 이미 탈퇴한 회원입니다." );
        }

        accountAccountStateRepository.save( accountAccountStateEntity );

    }


    @Transactional
    @Override
    public void deactivationAllByAccounts(List<String> accountIds) {
        for (String accountId : accountIds) {
            AccountEntity account = accountRepository.findById( accountId )
                .orElseThrow( () -> new NotFoundException( accountId ) );

            AccountStateEntity state = accountStateRepository.findById(
                AccountStatus.DEACTIVATED.getStatusValue() ).orElseThrow(
                () -> new NotFoundException( AccountStatus.DEACTIVATED.getStatusValue() )
            );

            AccountAccountStateEntity accountAccountStateEntity = AccountAccountStateEntity.builder()
                .account( account ).accountState( state )
                .accountState( state )
                .pk( Pk.builder().accountId( account.getId() ).accountStateCode( state.getCode() )
                    .changeAt( LocalDateTime.now() )
                    .build() )
                .build();

            // 만약 최근 상태 코드가 탈퇴이고, 다시 비활성화 명령이 들어오면 throw
            AccountStateEntity lastStateCode = accountAccountStateRepository
                .findTopByAccount_IdOrderByPk_ChangeAtDesc( accountId )
                .orElseThrow( () -> new RecentStateException( accountId ) )
                .getAccountState();

            if (lastStateCode.getCode().equals( state.getCode() )) {
                throw new RecentStateException( accountId + "이미 탈퇴한 회원입니다." );
            }

            accountAccountStateRepository.save( accountAccountStateEntity );
        }

    }

    private void lastStateCheck(String id, String accountStateCode) {
        if (accountStateCode.equals( AccountStatus.DEACTIVATED.getStatusValue() )) {
            throw new RecentStateException( id + "는 이미 탙퇴한 회원입니다." );
        }
    }

}
