package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.UserWallet;

public interface UserWalletService extends IService<UserWallet> {

  Page<UserWallet> findByPage(Page<UserWallet> page, Long userId);
}
