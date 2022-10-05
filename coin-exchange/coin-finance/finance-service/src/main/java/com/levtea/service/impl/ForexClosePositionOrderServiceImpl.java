package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.ForexClosePositionOrder;
import com.levtea.mapper.ForexClosePositionOrderMapper;
import com.levtea.service.ForexClosePositionOrderService;
import org.springframework.stereotype.Service;

@Service
public class ForexClosePositionOrderServiceImpl
    extends ServiceImpl<ForexClosePositionOrderMapper, ForexClosePositionOrder>
    implements ForexClosePositionOrderService {}
