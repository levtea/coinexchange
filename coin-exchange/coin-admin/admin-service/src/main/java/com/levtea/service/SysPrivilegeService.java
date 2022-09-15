package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.SysPrivilege;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege> {

  List<SysPrivilege> getAllSysPrivilege(Long id, Long roleId);
}
