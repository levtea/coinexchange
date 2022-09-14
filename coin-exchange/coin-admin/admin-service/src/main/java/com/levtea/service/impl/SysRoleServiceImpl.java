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

  /**
   * 判断一个用户是否为超级的管理员
   *
   * @param userId
   * @return
   */
  @Override
  public boolean isSuperAdmin(Long userId) {
    // 当用户的角色code 为：ROLE_ADMIN 时，该用户为超级的管理员
    // 用户的id->用户的角色->该角色的Code是否为ROLE_ADMIN
    String roleCode = sysRoleMapper.getUserRoleCode(userId);
    if (!StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN")) {
      return true;
    }
    return false;
  }
}
