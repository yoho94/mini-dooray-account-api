package com.nhn.minidooray.accountapi.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nhn.minidooray.accountapi.config.ValidationProperties.Account;
import com.nhn.minidooray.accountapi.domain.enums.AccountStateType;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@DataJpaTest
class AccountAccountStateRepositoryTest {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger( this.getClass() );

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountAccountStateRepository accountAccountStateRepository;

    @Autowired
    private AccountRepository accountRepository;



    @Test
    void save() {
        AccountEntity account = AccountEntity.builder()
            .id( "testAccountId2" )
            .name( "testAccountName2" )
            .password( "testAccountPassword2" )
            .email( "test2@email.com" )
            .build();

        AccountAccountStateEntity expectedAccountStateEntity = AccountAccountStateEntity.builder()
            .account( account )
            .pk( Pk.builder()
                .accountId( account.getId() )
                .accountStateCode( AccountStateType.REGISTER.getCode() )
                .changeAt( LocalDateTime.now() )
                .build() )
            .build();

        entityManager.persist(account);

        AccountAccountStateEntity savedAccountStateEntity = accountAccountStateRepository.save(
            expectedAccountStateEntity );

        assertNotNull(savedAccountStateEntity);
        assertEquals(AccountStateType.REGISTER.getCode(),
            savedAccountStateEntity.getPk().getAccountStateCode());
    }

    @Test
    void findById() {
        AccountEntity account = AccountEntity.builder()
            .id("testAccountId2")
            .name("testAccountName2")
            .password("testAccountPassword2")
            .email("test2@email.com")
            .build();

        Pk pk = Pk.builder()
            .accountId(account.getId())
            .accountStateCode(AccountStateType.REGISTER.getCode())
            .changeAt(LocalDateTime.now())
            .build();

        AccountAccountStateEntity expectedAccountStateEntity = AccountAccountStateEntity.builder()
            .account(account)
            .pk(pk)
            .build();

        entityManager.persist(account);
        entityManager.persist(expectedAccountStateEntity);

        AccountAccountStateEntity foundAccountStateEntity = accountAccountStateRepository.findById(pk)
            .get();

        assertNotNull( foundAccountStateEntity );
        assertThat(foundAccountStateEntity).isNotNull();
        assertThat(foundAccountStateEntity.getAccount().getId()).isEqualTo(account.getId());
        assertThat(foundAccountStateEntity.getPk().getAccountId()).isEqualTo(pk.getAccountId());
        assertThat(foundAccountStateEntity.getPk().getAccountStateCode()).isEqualTo(pk.getAccountStateCode());
        assertThat(foundAccountStateEntity.getPk().getChangeAt()).isEqualTo(pk.getChangeAt());
    }


    @Test
    void findAllByAccount_IdOrderByPk_ChangeAt() {
        String accountId = "testAccountId2";
        int pageSize = 5;

        AccountEntity account = AccountEntity.builder()
            .id(accountId)
            .name("testAccountName2")
            .password("testAccountPassword2")
            .email("test2@email.com")
            .build();

        List<AccountAccountStateEntity> stateEntities = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            AccountAccountStateEntity stateEntity = AccountAccountStateEntity.builder()
                .account(account)
                .pk(Pk.builder()
                    .accountId(account.getId())
                    .accountStateCode(AccountStateType.REGISTER.getCode())
                    .changeAt(LocalDateTime.now().minusDays(i))
                    .build())
                .build();

            entityManager.persist(stateEntity);
            stateEntities.add(stateEntity);
        }

        PageRequest pageRequest = PageRequest.of(0, pageSize, Sort.by("pk.changeAt").ascending());
        Page<AccountAccountStateEntity> page = accountAccountStateRepository
            .findAllByAccount_IdOrderByPk_ChangeAt(accountId, pageRequest);

        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getContent().size()).isEqualTo(5);
    }


    @Test
    void findTopByAccount_IdOrderByPk_ChangeAtDesc() {

        String accountId = "testAccountId2";
        AccountEntity account = AccountEntity.builder()
            .id( accountId )
            .name( "testAccountName2" )
            .email( "testAccountName2" )
            .password( "testPassword2" )
            .build();
        entityManager.persist( account );
        Pk pk = Pk.builder()
            .accountId( account.getId() )
            .accountStateCode( AccountStateType.REGISTER.getCode() )
            .changeAt( LocalDateTime.now() )
            .build();
        AccountAccountStateEntity accountAccountStateEntity = AccountAccountStateEntity
            .builder()
            .account( account )
            .pk( pk)
            .build();
        accountAccountStateRepository.save( accountAccountStateEntity );

        pk.setAccountStateCode( AccountStateType.DORMANT.getCode() );
        pk.setChangeAt( LocalDateTime.now().plusHours( 1L ) );

       AccountAccountStateEntity topChange= accountAccountStateRepository.findTopByAccount_IdOrderByPk_ChangeAtDesc(accountId ).get();

        assertNotNull( topChange );
        assertEquals( AccountStateType.DORMANT.getCode(),topChange.getPk().getAccountStateCode() );

    }
}