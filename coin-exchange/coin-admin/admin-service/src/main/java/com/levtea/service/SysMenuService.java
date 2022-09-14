package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

  List<SysMenu> getMenusByUserId(Long userId);
}

