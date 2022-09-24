package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.UserAuthInfo;

import java.util.List;

public interface UserAuthInfoService extends IService<UserAuthInfo> {

  List<UserAuthInfo> getUserAuthInfoByUserId(Long id);

  List<UserAuthInfo> getUserAuthInfoByCode(Long authCode);
}
