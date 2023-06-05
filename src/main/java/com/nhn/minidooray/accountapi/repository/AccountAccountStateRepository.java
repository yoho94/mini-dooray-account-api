package com.nhn.minidooray.accountapi.repository;

import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity;
import com.nhn.minidooray.accountapi.entity.AccountAccountStateEntity.Pk;
import com.nhn.minidooray.accountapi.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountAccountStateRepository extends JpaRepository<AccountAccountStateEntity, Pk> {
  public List<AccountAccountStateEntity> findAllByAccount_IdAndAccountState_Code(String accountId, String accountStateCode);
 public List<AccountAccountStateEntity> findAllByAccountStateCode(String accountStateCode);

  public List<AccountAccountStateEntity> findAllByAccount_Id(String accountId);

  public List<AccountAccountStateEntity> findAllByAccountIn(List<AccountEntity> accountEntities);

  Optional<AccountAccountStateEntity> findTopByAccount_IdOrderByPk_ChangeAtDesc(String accountId);
}