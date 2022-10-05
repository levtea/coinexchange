package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CoinBalance;
import com.levtea.mapper.CoinBalanceMapper;
import com.levtea.service.CoinBalanceService;
import org.springframework.stereotype.Service;

@Service
public class CoinBalanceServiceImpl extends ServiceImpl<CoinBalanceMapper, CoinBalance>
    implements CoinBalanceService {}
