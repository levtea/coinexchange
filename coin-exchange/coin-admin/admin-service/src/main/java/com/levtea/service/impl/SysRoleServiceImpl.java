package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.SysRole;
import com.levtea.mapper.SysRoleMapper;
import com.levtea.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService {

  @Autowired private SysRoleMapper sysRoleMapper;

  @Override
  public boolean isSuperAdmin(Long userId) {
    String roleCode = sysRoleMapper.getUserRoleCode(userId);
    if (!StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN")) {
      return true;
    }
    return false;
  }
}
