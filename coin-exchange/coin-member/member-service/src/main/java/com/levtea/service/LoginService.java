package com.levtea.service;

import com.levtea.model.LoginForm;
import com.levtea.model.LoginUser;

public interface LoginService {
  LoginUser login(LoginForm loginForm);
}
