package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CoinWithdrawAuditRecord;
import com.levtea.mapper.CoinWithdrawAuditRecordMapper;
import com.levtea.service.CoinWithdrawAuditRecordService;
import org.springframework.stereotype.Service;

@Service
public class CoinWithdrawAuditRecordServiceImpl
    extends ServiceImpl<CoinWithdrawAuditRecordMapper, CoinWithdrawAuditRecord>
    implements CoinWithdrawAuditRecordService {}
