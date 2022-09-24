package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.UserAuthAuditRecord;
import com.levtea.mapper.UserAuthAuditRecordMapper;
import com.levtea.service.UserAuthAuditRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthAuditRecordServiceImpl
    extends ServiceImpl<UserAuthAuditRecordMapper, UserAuthAuditRecord>
    implements UserAuthAuditRecordService {

  @Override
  public List<UserAuthAuditRecord> getUserAuthAuditRecordList(Long id) {
    return list(
        new LambdaQueryWrapper<UserAuthAuditRecord>()
            .eq(UserAuthAuditRecord::getUserId, id)
            .orderByDesc(UserAuthAuditRecord::getCreated)
            .last("limit 3"));
  }
}
