package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.Account;

import java.math.BigDecimal;

public interface AccountService extends IService<Account> {

  Boolean transferAccountAmount(
      Long adminId,
      Long userId,
      Long coinId,
      Long orderNum,
      BigDecimal num,
      BigDecimal fee,
      String remark,
      String businessType,
      Byte direction);

  Boolean decreaseAccountAmount(
      Long adminId,
      Long userId,
      Long coinId,
      Long orderNum,
      BigDecimal num,
      BigDecimal fee,
      String remark,
      String businessType,
      byte direction);

  Account findByUserAndCoin(Long userId, String coinName);
}
