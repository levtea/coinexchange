package com.levtea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.AddressPool;
import com.levtea.mapper.AddressPoolMapper;
import com.levtea.service.AddressPoolService;
import org.springframework.stereotype.Service;

@Service
public class AddressPoolServiceImpl extends ServiceImpl<AddressPoolMapper, AddressPool>
    implements AddressPoolService {}
