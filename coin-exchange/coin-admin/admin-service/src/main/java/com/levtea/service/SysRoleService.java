package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.SysRole;

public interface SysRoleService extends IService<SysRole> {

  boolean isSuperAdmin(Long userId);
}

