package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.Account;
import com.levtea.domain.AccountDetail;
import com.levtea.domain.Coin;
import com.levtea.domin.Config;
import com.levtea.mapper.AccountMapper;
import com.levtea.service.AccountDetailService;
import com.levtea.service.AccountService;
import com.levtea.service.CoinService;
import com.levtea.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService {

  @Autowired private AccountDetailService accountDetailService;

  @Autowired private CoinService coinService;

  @Autowired private ConfigService configService;

  @Override
  public Boolean transferAccountAmount(
      Long adminId,
      Long userId,
      Long coinId,
      Long orderNum,
      BigDecimal num,
      BigDecimal fee,
      String remark,
      String businessType,
      Byte direction) {
    Account coinAccount = getCoinAccount(coinId, userId);
    if (coinAccount == null) {
      throw new IllegalArgumentException("用户当前的该币种的余额不存在");
    }
    // 增加一条流水记录
    AccountDetail accountDetail = new AccountDetail();
    accountDetail.setCoinId(coinId);
    accountDetail.setUserId(userId);
    accountDetail.setAmount(num);
    accountDetail.setFee(fee);
    accountDetail.setOrderId(orderNum);
    accountDetail.setAccountId(coinAccount.getId());
    accountDetail.setRefAccountId(coinAccount.getId());
    accountDetail.setRemark(remark);
    accountDetail.setBusinessType(businessType);
    accountDetail.setDirection(direction);
    accountDetail.setCreated(new Date());
    boolean save = accountDetailService.save(accountDetail);
    if (save) { // 用户余额的增加
      coinAccount.setBalanceAmount(coinAccount.getBalanceAmount().add(num));
      boolean updateById = updateById(coinAccount);
      return updateById;
    }
    return save;
  }

  @Override
  public Boolean decreaseAccountAmount(
      Long adminId,
      Long userId,
      Long coinId,
      Long orderNum,
      BigDecimal num,
      BigDecimal fee,
      String remark,
      String businessType,
      byte direction) {
    Account coinAccount = getCoinAccount(coinId, userId);
    if (coinAccount == null) {
      throw new IllegalArgumentException("账户不存在");
    }
    AccountDetail accountDetail = new AccountDetail();
    accountDetail.setUserId(userId);
    accountDetail.setCoinId(coinId);
    accountDetail.setAmount(num);
    accountDetail.setFee(fee);
    accountDetail.setAccountId(coinAccount.getId());
    accountDetail.setRefAccountId(coinAccount.getId());
    accountDetail.setRemark(remark);
    accountDetail.setBusinessType(businessType);
    accountDetail.setDirection(direction);
    boolean save = accountDetailService.save(accountDetail);
    if (save) { // 新增了流水记录
      BigDecimal balanceAmount = coinAccount.getBalanceAmount();
      BigDecimal result = balanceAmount.add(num.multiply(BigDecimal.valueOf(-1)));
      if (result.compareTo(BigDecimal.ONE) > 0) {
        coinAccount.setBalanceAmount(result);
        return updateById(coinAccount);
      } else {
        throw new IllegalArgumentException("余额不足");
      }
    }
    return false;
  }

  @Override
  public Account findByUserAndCoin(Long userId, String coinName) {
    Coin coin = coinService.getCoinByCoinName(coinName);
    if (coin == null) {
      throw new IllegalArgumentException("货币不存在");
    }
    Account account =
        getOne(
            new LambdaQueryWrapper<Account>()
                .eq(Account::getUserId, userId)
                .eq(Account::getCoinId, coin.getId()));
    if (account == null) {
      throw new IllegalArgumentException("该资产不存在");
    }

    Config sellRateConfig = configService.getConfigByCode("USDT2CNY");
    account.setSellRate(new BigDecimal(sellRateConfig.getValue())); // 出售的费率

    Config setBuyRateConfig = configService.getConfigByCode("CNY2USDT");
    account.setBuyRate(new BigDecimal(setBuyRateConfig.getValue())); // 买进来的费率

    return account;
  }

  private Account getCoinAccount(Long coinId, Long userId) {

    return getOne(
        new LambdaQueryWrapper<Account>()
            .eq(Account::getCoinId, coinId)
            .eq(Account::getUserId, userId)
            .eq(Account::getStatus, 1));
  }
}
