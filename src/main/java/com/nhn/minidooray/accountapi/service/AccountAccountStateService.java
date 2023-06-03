package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDto;
import java.util.List;
import java.util.Optional;


public interface AccountAccountStateService {
  public Optional<AccountAccountStateDto> save(AccountAccountStateDto accountAccountStateDto);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 업데이트 하지 않습니다.", forRemoval = true)
  public Optional<AccountAccountStateDto> update(AccountAccountStateDto accountAccountStateDto);
  public List<AccountAccountStateDto> findAllByAccountIdAndAccountStateCode(String accountId, String accountStateCode);
  public List<AccountAccountStateDto> findAll();
  public List<AccountAccountStateDto> findAllByAccountStateCode(String accountStateCode);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void delete(AccountAccountStateDto accountAccountStateDto);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void deleteByAccountIdAndAccountStateCode(String accountId, String accountStateCode);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void deleteAllByAccountId(String accountId);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void deleteAllByAccountStateCode(String accountStateCode);

}
