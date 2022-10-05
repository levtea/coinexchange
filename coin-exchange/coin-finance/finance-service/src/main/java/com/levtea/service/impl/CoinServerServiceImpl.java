package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CoinServer;
import com.levtea.mapper.CoinServerMapper;
import com.levtea.service.CoinServerService;
import org.springframework.stereotype.Service;

@Service
public class CoinServerServiceImpl extends ServiceImpl<CoinServerMapper, CoinServer>
    implements CoinServerService {}
