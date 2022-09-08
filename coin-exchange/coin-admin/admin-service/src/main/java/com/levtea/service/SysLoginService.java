package com.levtea.service;

import com.levtea.model.LoginResult;

public interface SysLoginService {

  LoginResult login(String username, String password);
}
