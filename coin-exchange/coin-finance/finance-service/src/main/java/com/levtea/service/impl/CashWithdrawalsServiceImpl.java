package com.levtea.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CashWithdrawAuditRecord;
import com.levtea.domain.CashWithdrawals;
import com.levtea.dto.UserDto;
import com.levtea.feign.UserServiceFeign;
import com.levtea.mapper.CashWithdrawAuditRecordMapper;
import com.levtea.mapper.CashWithdrawalsMapper;
import com.levtea.service.AccountService;
import com.levtea.service.CashWithdrawalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CashWithdrawalsServiceImpl extends ServiceImpl<CashWithdrawalsMapper, CashWithdrawals>
    implements CashWithdrawalsService {

  @Autowired private UserServiceFeign userServiceFeign;

  //  @Autowired private ConfigService configService;
  //
  //  @Autowired private StringRedisTemplate redisTemplate;
  //
  //  @Autowired private UserBankServiceFeign userBankServiceFeign;

  @Autowired private AccountService accountService;

  @Autowired private CashWithdrawAuditRecordMapper cashWithdrawAuditRecordMapper;

  @CreateCache(
      name = "CASH_WITHDRAWALS_LOCK:",
      expire = 100,
      timeUnit = TimeUnit.SECONDS,
      cacheType = CacheType.BOTH)
  private Cache<String, String> lock;

  @Override
  public Page<CashWithdrawals> findByPage(
      Page<CashWithdrawals> page,
      Long userId,
      String userName,
      String mobile,
      Byte status,
      String numMin,
      String numMax,
      String startTime,
      String endTime) {
    // 有用户的信息时
    Map<Long, UserDto> basicUsers = null;
    LambdaQueryWrapper<CashWithdrawals> cashWithdrawalsLambdaQueryWrapper =
        new LambdaQueryWrapper<>();
    if (userId != null || !StringUtils.isEmpty(userName) || !StringUtils.isEmpty(mobile)) {
      basicUsers =
          userServiceFeign.getBasicUsers(
              userId == null ? null : Arrays.asList(userId), userName, mobile);
      if (CollectionUtils.isEmpty(basicUsers)) {
        return page;
      }
      Set<Long> userIds = basicUsers.keySet();
      cashWithdrawalsLambdaQueryWrapper.in(CashWithdrawals::getUserId, userIds);
    }
    // 其他的查询信息
    cashWithdrawalsLambdaQueryWrapper
        .eq(status != null, CashWithdrawals::getStatus, status)
        .between(
            !(StringUtils.isEmpty(numMin) || StringUtils.isEmpty(numMax)),
            CashWithdrawals::getNum,
            new BigDecimal(StringUtils.isEmpty(numMin) ? "0" : numMin),
            new BigDecimal(StringUtils.isEmpty(numMax) ? "0" : numMax))
        .between(
            !(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)),
            CashWithdrawals::getCreated,
            startTime,
            endTime + " 23:59:59");
    Page<CashWithdrawals> pageDate = page(page, cashWithdrawalsLambdaQueryWrapper);
    List<CashWithdrawals> records = pageDate.getRecords();
    if (!CollectionUtils.isEmpty(records)) {
      List<Long> userIds =
          records.stream().map(CashWithdrawals::getUserId).collect(Collectors.toList());
      if (basicUsers == null) {
        basicUsers = userServiceFeign.getBasicUsers(userIds, null, null);
      }
      Map<Long, UserDto> finalBasicUsers = basicUsers;
      records.forEach(
          cashWithdrawals -> {
            UserDto userDto = finalBasicUsers.get(cashWithdrawals.getUserId());
            if (userDto != null) {
              cashWithdrawals.setUsername(userDto.getUsername());
              cashWithdrawals.setRealName(userDto.getRealName());
            }
          });
    }
    return pageDate;
  }

  @Override
  public boolean updateWithdrawalsStatus(
      Long userId, CashWithdrawAuditRecord cashWithdrawAuditRecord) {
    // 1 使用锁锁住
    boolean isOk =
        lock.tryLockAndRun(
            cashWithdrawAuditRecord.getId() + "",
            300,
            TimeUnit.SECONDS,
            () -> {
              CashWithdrawals cashWithdrawals = getById(cashWithdrawAuditRecord.getId());
              if (cashWithdrawals == null) {
                throw new IllegalArgumentException("现金的审核记录不存在");
              }

              // 2 添加一个审核的记录
              CashWithdrawAuditRecord cashWithdrawAuditRecordNew = new CashWithdrawAuditRecord();
              cashWithdrawAuditRecordNew.setAuditUserId(userId);
              cashWithdrawAuditRecordNew.setRemark(cashWithdrawAuditRecord.getRemark());
              cashWithdrawAuditRecordNew.setCreated(new Date());
              cashWithdrawAuditRecordNew.setStatus(cashWithdrawAuditRecord.getStatus());
              Integer step = cashWithdrawals.getStep() + 1;
              cashWithdrawAuditRecordNew.setStep(step.byteValue());
              cashWithdrawAuditRecordNew.setOrderId(cashWithdrawals.getId());

              // 记录保存成功
              int count = cashWithdrawAuditRecordMapper.insert(cashWithdrawAuditRecordNew);
              if (count > 0) {
                cashWithdrawals.setStatus(cashWithdrawAuditRecord.getStatus());
                cashWithdrawals.setRemark(cashWithdrawAuditRecord.getRemark());
                cashWithdrawals.setLastTime(new Date());
                cashWithdrawals.setAccountId(userId); //
                cashWithdrawals.setStep(step.byteValue());
                boolean updateById = updateById(cashWithdrawals); // 审核拒绝
                if (updateById) {
                  // 审核通过 withdrawals_out
                  Boolean isPass =
                      accountService.decreaseAccountAmount(
                          userId,
                          cashWithdrawals.getUserId(),
                          cashWithdrawals.getCoinId(),
                          cashWithdrawals.getId(),
                          cashWithdrawals.getNum(),
                          cashWithdrawals.getFee(),
                          cashWithdrawals.getRemark(),
                          "withdrawals_out",
                          (byte) 2);
                }
              }
            });

    return isOk;
  }

  @Override
  public Page<CashWithdrawals> findCashWithdrawals(
      Page<CashWithdrawals> page, Long userId, Byte status) {
    return page(
        page,
        new LambdaQueryWrapper<CashWithdrawals>()
            .eq(CashWithdrawals::getUserId, userId)
            .eq(status != null, CashWithdrawals::getStatus, status));
  }
}
