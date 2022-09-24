package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.User;
import com.levtea.dto.UserDto;
import com.levtea.model.*;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
  Page<User> findByPage(
      Page<User> page,
      String mobile,
      Long userId,
      String userName,
      String realName,
      Integer status,
      Integer reviewStatus);

  Page<User> findDirectInvitePage(Page<User> page, Long userId);

  void updateUserAuthStatus(Long id, Byte authStatus, Long authCode, String remark);

  boolean identifyVerify(Long id, UserAuthForm userAuthForm);

  boolean register(RegisterParam registerParam);

  void authUser(Long id, List<String> imgs);

  boolean updatePhone(Long userId, UpdatePhoneParam updatePhoneParam);

  boolean checkNewPhone(String mobile, String countryCode);

  boolean updateUserLoginPwd(Long userId, UpdateLoginParam updateLoginParam);

  boolean updateUserPayPwd(Long userId, UpdateLoginParam updateLoginParam);

  boolean unsetPayPassword(Long userId, UnsetPayPasswordParam unsetPayPasswordParam);

  List<User> getUserInvites(Long userId);

  Map<Long, UserDto> getBasicUsers(List<Long> ids, String userName, String mobile);

  boolean unsetLoginPwd(UnsetPasswordParam unSetPasswordParam);
}
