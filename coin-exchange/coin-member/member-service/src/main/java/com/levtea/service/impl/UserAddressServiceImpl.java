package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.UserAddress;
import com.levtea.mapper.UserAddressMapper;
import com.levtea.service.UserAddressService;
import org.springframework.stereotype.Service;

@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress>
    implements UserAddressService {

  @Override
  public Page<UserAddress> findByPage(Page<UserAddress> page, Long userId) {
    return page(page, new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, userId));
  }
}
