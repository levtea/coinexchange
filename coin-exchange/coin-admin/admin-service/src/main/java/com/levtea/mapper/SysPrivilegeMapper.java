package com.levtea.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.levtea.domain.SysPrivilege;

import java.util.Set;

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
  Set<Long> getPrivilegesByRoleId(Long roleId);
}
