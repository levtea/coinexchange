package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.SysUserLog;
import com.levtea.mapper.SysUserLogMapper;
import com.levtea.service.SysUserLogService;
import org.springframework.stereotype.Service;

@Service
public class SysUserLogServiceImpl extends ServiceImpl<SysUserLogMapper, SysUserLog>
    implements SysUserLogService {}
