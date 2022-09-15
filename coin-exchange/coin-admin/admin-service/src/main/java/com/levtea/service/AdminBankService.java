package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.AdminBank;

public interface AdminBankService extends IService<AdminBank> {

  Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);
}
