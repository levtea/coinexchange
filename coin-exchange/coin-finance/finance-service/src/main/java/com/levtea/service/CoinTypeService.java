package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.CoinType;

import java.util.List;

public interface CoinTypeService extends IService<CoinType> {

  Page<CoinType> findByPage(Page<CoinType> page, String code);

  List<CoinType> listByStatus(Byte status);
}
