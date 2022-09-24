package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.UserBank;

public interface UserBankService extends IService<UserBank> {

  Page<UserBank> findByPage(Page<UserBank> page, Long usrId);

  UserBank getCurrentUserBank(Long userId);

  boolean bindBank(Long userId, UserBank userBank);
}
