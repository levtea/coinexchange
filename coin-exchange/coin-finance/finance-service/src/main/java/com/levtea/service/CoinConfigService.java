package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.CoinConfig;

public interface CoinConfigService extends IService<CoinConfig> {

  CoinConfig findByCoinId(Long coinId);

  boolean updateOrSave(CoinConfig coinConfig);
}
