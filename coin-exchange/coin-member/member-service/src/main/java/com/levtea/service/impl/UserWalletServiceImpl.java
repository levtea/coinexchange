package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.UserWallet;
import com.levtea.mapper.UserWalletMapper;
import com.levtea.service.UserWalletService;
import org.springframework.stereotype.Service;

@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet>
    implements UserWalletService {

  @Override
  public Page<UserWallet> findByPage(Page<UserWallet> page, Long userId) {
    return page(page, new LambdaQueryWrapper<UserWallet>().eq(UserWallet::getUserId, userId));
  }
}
