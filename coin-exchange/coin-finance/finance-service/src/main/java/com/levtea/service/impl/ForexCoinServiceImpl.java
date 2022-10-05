package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.ForexCoin;
import com.levtea.mapper.ForexCoinMapper;
import com.levtea.service.ForexCoinService;
import org.springframework.stereotype.Service;

@Service
public class ForexCoinServiceImpl extends ServiceImpl<ForexCoinMapper, ForexCoin>
    implements ForexCoinService {}
