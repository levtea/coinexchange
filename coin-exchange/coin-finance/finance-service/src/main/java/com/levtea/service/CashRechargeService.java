package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.CashRecharge;
import com.levtea.domain.CashRechargeAuditRecord;
import com.levtea.model.CashParam;
import com.levtea.vo.CashTradeVo;

public interface CashRechargeService extends IService<CashRecharge> {

  Page<CashRecharge> findByPage(
      Page<CashRecharge> page,
      Long coinId,
      Long userId,
      String userName,
      String mobile,
      Byte status,
      String numMin,
      String numMax,
      String startTime,
      String endTime);

  Page<CashRecharge> findUserCashRecharge(Page<CashRecharge> page, Long userId, Byte status);

  boolean cashRechargeAudit(Long userId, CashRechargeAuditRecord cashRechargeAuditRecord);

  CashTradeVo buy(Long userId, CashParam cashParam);
}
