package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CashWithdrawAuditRecord;
import com.levtea.mapper.CashWithdrawAuditRecordMapper;
import com.levtea.service.CashWithdrawAuditRecordService;
import org.springframework.stereotype.Service;

@Service
public class CashWithdrawAuditRecordServiceImpl
    extends ServiceImpl<CashWithdrawAuditRecordMapper, CashWithdrawAuditRecord>
    implements CashWithdrawAuditRecordService {}
