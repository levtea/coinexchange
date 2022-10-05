package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.UserAccountFreeze;
import com.levtea.mapper.UserAccountFreezeMapper;
import com.levtea.service.UserAccountFreezeService;
import org.springframework.stereotype.Service;

@Service
public class UserAccountFreezeServiceImpl
    extends ServiceImpl<UserAccountFreezeMapper, UserAccountFreeze>
    implements UserAccountFreezeService {}
