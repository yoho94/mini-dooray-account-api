package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAccountStateRepository extends
    JpaRepository<AccountAccountStateEntity, Pk> {


    Page<AccountAccountStateEntity> findAllByAccount_IdOrderByPk_ChangeAt(String accountId,
        Pageable pageable);


    Optional<AccountAccountStateEntity> findTopByAccount_IdOrderByPk_ChangeAtDesc(String accountId);
}