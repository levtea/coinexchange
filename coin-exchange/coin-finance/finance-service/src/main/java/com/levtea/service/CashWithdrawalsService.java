package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.CashWithdrawAuditRecord;
import com.levtea.domain.CashWithdrawals;

public interface CashWithdrawalsService extends IService<CashWithdrawals> {

  Page<CashWithdrawals> findByPage(
      Page<CashWithdrawals> page,
      Long userId,
      String userName,
      String mobile,
      Byte status,
      String numMin,
      String numMax,
      String startTime,
      String endTime);

  boolean updateWithdrawalsStatus(Long userId, CashWithdrawAuditRecord cashWithdrawAuditRecord);

  Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status);
}
