package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

  /**
   * 通过用户的id 查询用户的菜单数据
   *
   * @param userId
   * @return
   */
  List<SysMenu> getMenusByUserId(Long userId);
}
