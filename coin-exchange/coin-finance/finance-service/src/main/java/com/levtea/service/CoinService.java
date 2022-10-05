package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.Coin;

import java.util.List;

public interface CoinService extends IService<Coin> {

  Page<Coin> findByPage(
      String name, String type, Byte status, String title, String walletType, Page<Coin> page);

  List<Coin> getCoinsByStatus(Byte status);

  Coin getCoinByCoinName(String coinName);
}
