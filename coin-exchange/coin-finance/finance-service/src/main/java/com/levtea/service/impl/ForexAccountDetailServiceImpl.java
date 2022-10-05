package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.ForexAccountDetail;
import com.levtea.mapper.ForexAccountDetailMapper;
import com.levtea.service.ForexAccountDetailService;
import org.springframework.stereotype.Service;

@Service
public class ForexAccountDetailServiceImpl
    extends ServiceImpl<ForexAccountDetailMapper, ForexAccountDetail>
    implements ForexAccountDetailService {}
