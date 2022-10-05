package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.AdminAddress;

public interface AdminAddressService extends IService<AdminAddress> {

  Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId);
}
