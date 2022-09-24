package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.User;
import com.levtea.domain.UserBank;
import com.levtea.mapper.UserBankMapper;
import com.levtea.service.UserBankService;
import com.levtea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank>
    implements UserBankService {

  @Autowired private UserService userService;

  @Override
  public Page<UserBank> findByPage(Page<UserBank> page, Long usrId) {
    return page(
        page, new LambdaQueryWrapper<UserBank>().eq(usrId != null, UserBank::getUserId, usrId));
  }

  @Override
  public UserBank getCurrentUserBank(Long userId) {
    UserBank userBank =
        getOne(
            new LambdaQueryWrapper<UserBank>()
                .eq(UserBank::getUserId, userId)
                .eq(UserBank::getStatus, 1));
    return userBank;
  }

  @Override
  public boolean bindBank(Long userId, UserBank userBank) {
    // 支付密码的判断
    String payPassword = userBank.getPayPassword();
    User user = userService.getById(userId);
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    if (!bCryptPasswordEncoder.matches(payPassword, user.getPaypassword())) {
      throw new IllegalArgumentException("用户的支付密码错误");
    }
    Long id = userBank.getId(); // 有Id 代表是修改操作
    if (id != null) {
      UserBank userBankDb = getById(id);
      if (userBankDb == null) {
        throw new IllegalArgumentException("用户的银行卡的ID输入错误");
      }
      return updateById(userBank); // 修改值
    }
    // 若银行卡的id为null ,则需要新建一个
    userBank.setUserId(userId);
    return save(userBank);
  }
}
