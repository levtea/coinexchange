package com.levtea.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.levtea.domain.SysMenu;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
  List<SysMenu> selectMenusByUserId(Long userId);
}
