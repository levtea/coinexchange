package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.ForexAccount;
import com.levtea.mapper.ForexAccountMapper;
import com.levtea.service.ForexAccountService;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountServiceImpl extends ServiceImpl<ForexAccountMapper, ForexAccount>
    implements ForexAccountService {}
