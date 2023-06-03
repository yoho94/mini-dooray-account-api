package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {

}
