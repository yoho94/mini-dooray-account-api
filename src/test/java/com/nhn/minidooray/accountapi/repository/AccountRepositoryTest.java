package com.nhn.minidooray.accountapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.nhn.minidooray.accountapi.entity.AccountEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        AccountEntity accountEntity = AccountEntity
            .builder()
            .id( "testAccountId" )
            .name( "testAccountName" )
            .password( "testAccountPassword" )
            .email( "test@example.com" )
            .build();
        accountRepository.save( accountEntity );
    }

    @Test
    void save() {
        AccountEntity accountEntity = AccountEntity
            .builder()
            .id( "testAccountId2" )
            .name( "testAccountName2" )
            .password( "testAccountPassword2" )
            .email( "test2@example.com" )
            .build();
        AccountEntity saved = accountRepository.save( accountEntity );

        assertEquals( "testAccountId2", saved.getId() );
    }

    @Test
    void findById() {
        String id = "testAccountId";

        Optional<AccountEntity> accountEntity = accountRepository.findById( id );

        assertNotNull( accountEntity );

        assertEquals( id, accountEntity.get().getId() );
    }

    @Test
    void findByEmail() {
        String email = "test@example.com";
        Optional<AccountEntity> accountEntity = accountRepository.findByEmail( email );
        assertNotNull( accountEntity );
        assertEquals( email, accountEntity.get().getEmail() );

    }


}