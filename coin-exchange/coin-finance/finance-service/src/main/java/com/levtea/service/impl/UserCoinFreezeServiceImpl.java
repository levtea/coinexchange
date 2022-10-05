package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.UserCoinFreeze;
import com.levtea.mapper.UserCoinFreezeMapper;
import com.levtea.service.UserCoinFreezeService;
import org.springframework.stereotype.Service;

@Service
public class UserCoinFreezeServiceImpl extends ServiceImpl<UserCoinFreezeMapper, UserCoinFreeze>
    implements UserCoinFreezeService {}
