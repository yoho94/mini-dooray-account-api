package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.domain.dto.AccountStateDto;
import com.nhn.minidooray.accountapi.entity.AccountStateEntity;
import java.util.List;
import java.util.Optional;

public interface AccountStateService {
  public Optional<AccountStateDto> save(AccountStateDto accountStateDto);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 업데이트 하지 않습니다.", forRemoval = true)
  public Optional<AccountStateDto> update(AccountStateDto accountStateDto);

  public Optional<AccountStateDto> findByCode(String code);

  public List<AccountStateDto> findAll();


  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void delete(AccountStateDto accountStateDto);

  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void deleteByCode(String code);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void deleteAll();

}
