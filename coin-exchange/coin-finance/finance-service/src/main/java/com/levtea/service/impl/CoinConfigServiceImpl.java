package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.Coin;
import com.levtea.domain.CoinConfig;
import com.levtea.mapper.CoinConfigMapper;
import com.levtea.service.CoinConfigService;
import com.levtea.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinConfigServiceImpl extends ServiceImpl<CoinConfigMapper, CoinConfig>
    implements CoinConfigService {

  @Autowired private CoinService coinService;

  @Override
  public CoinConfig findByCoinId(Long coinId) {
    return getOne(new LambdaQueryWrapper<CoinConfig>().eq(CoinConfig::getId, coinId));
  }

  @Override
  public boolean updateOrSave(CoinConfig coinConfig) {
    //
    Coin coin = coinService.getById(coinConfig.getId());
    if (coin == null) {
      throw new IllegalArgumentException("coin-Id不存在");
    }
    coinConfig.setCoinType(coin.getType());
    coinConfig.setName(coin.getName());
    // 如何是新增/修改呢?
    CoinConfig config = getById(coinConfig.getId());
    if (config == null) { // 新增操作
      return save(coinConfig);
    } else { // 修改操作
      return updateById(coinConfig);
    }
  }
}
