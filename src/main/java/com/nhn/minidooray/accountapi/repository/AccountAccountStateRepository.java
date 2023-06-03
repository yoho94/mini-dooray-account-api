package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAccountStateRepository extends JpaRepository<AccountAccountStateEntity, Pk> {

}
