package com.levtea.service;

import com.levtea.model.LoginResult;

/** 登录的接口 */
public interface SysLoginService {

  /**
   * 登录的实现
   *
   * @param username 用户名
   * @param password
   *     <p>用户的密码
   * @return 登录的结果
   */
  LoginResult login(String username, String password);
}
