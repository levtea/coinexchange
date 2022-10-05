package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.AccountDetail;

public interface AccountDetailService extends IService<AccountDetail> {

  Page<AccountDetail> findByPage(
      Page<AccountDetail> page,
      Long coinId,
      Long accountId,
      Long userId,
      String userName,
      String mobile,
      String amountStart,
      String amountEnd,
      String startTime,
      String endTime);
}
