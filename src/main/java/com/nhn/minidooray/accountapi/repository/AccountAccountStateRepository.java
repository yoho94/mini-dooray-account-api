package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAccountStateRepository extends JpaRepository<AccountAccountStateEntity, Pk> {
  public List<AccountAccountStateEntity> findAllByAccount_IdAndAccountState_Code(String accountId, String accountStateCode);
 public List<AccountAccountStateEntity> findAllByAccountStateCode(String accountStateCode);

  public List<AccountAccountStateEntity> findAllByAccount_Id(String accountId);

  public List<AccountAccountStateEntity> findAllByAccountIn(List<AccountEntity> accountEntities);
}