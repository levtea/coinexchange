package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.SysRolePrivilegeUser;
import com.levtea.mapper.SysRolePrivilegeUserMapper;
import com.levtea.service.SysRolePrivilegeUserService;
import org.springframework.stereotype.Service;

@Service
public class SysRolePrivilegeUserServiceImpl
    extends ServiceImpl<SysRolePrivilegeUserMapper, SysRolePrivilegeUser>
    implements SysRolePrivilegeUserService {}
