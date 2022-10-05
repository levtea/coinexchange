package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.ForexOpenPositionOrder;
import com.levtea.mapper.ForexOpenPositionOrderMapper;
import com.levtea.service.ForexOpenPositionOrderService;
import org.springframework.stereotype.Service;

@Service
public class ForexOpenPositionOrderServiceImpl
    extends ServiceImpl<ForexOpenPositionOrderMapper, ForexOpenPositionOrder>
    implements ForexOpenPositionOrderService {}
