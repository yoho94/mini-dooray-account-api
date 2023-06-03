package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStateRepository extends JpaRepository<AccountStateEntity, String> {

}
