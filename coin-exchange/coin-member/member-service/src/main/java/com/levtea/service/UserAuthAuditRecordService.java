package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.UserAuthAuditRecord;

import java.util.List;

public interface UserAuthAuditRecordService extends IService<UserAuthAuditRecord> {

  List<UserAuthAuditRecord> getUserAuthAuditRecordList(Long id);
}
