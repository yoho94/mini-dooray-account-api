package com.nhn.minidooray.accountapi.service;

import com.nhn.minidooray.accountapi.domain.dto.AccountAccountStateDetailDto;
import java.util.List;
import java.util.Optional;

public interface AccountAccountStateDetailService {
  public Optional<AccountAccountStateDetailDto> save(AccountAccountStateDetailDto accountAccountStateDetailDto);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 업데이트 하지 않습니다.", forRemoval = true)
  public Optional<AccountAccountStateDetailDto> update(AccountAccountStateDetailDto accountAccountStateDetailDto);
  public List<AccountAccountStateDetailDto> findAllByAccountIdAndAccountStateCode(String accountId, String accountStateCode);
  public List<AccountAccountStateDetailDto> findAll();
  public List<AccountAccountStateDetailDto> findAllByAccountStateCode(String accountStateCode);
  /**
   * @deprecated
   */
  @Deprecated(since = "일반적인 경우에 삭제 하지 않습니다.", forRemoval = true)
  public void delete(AccountAccountStateDetailDto accountAccountStateDetailDto);
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
