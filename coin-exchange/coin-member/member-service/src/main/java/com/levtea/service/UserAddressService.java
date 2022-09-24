package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.UserAddress;

public interface UserAddressService extends IService<UserAddress> {

  Page<UserAddress> findByPage(Page<UserAddress> page, Long userId);
}
