package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CashRechargeAuditRecord;
import com.levtea.mapper.CashRechargeAuditRecordMapper;
import com.levtea.service.CashRechargeAuditRecordService;
import org.springframework.stereotype.Service;

@Service
public class CashRechargeAuditRecordServiceImpl
    extends ServiceImpl<CashRechargeAuditRecordMapper, CashRechargeAuditRecord>
    implements CashRechargeAuditRecordService {}
