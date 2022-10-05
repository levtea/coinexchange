package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CoinWithdraw;
import com.levtea.dto.UserDto;
import com.levtea.feign.UserServiceFeign;
import com.levtea.mapper.CoinWithdrawMapper;
import com.levtea.service.CoinWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CoinWithdrawServiceImpl extends ServiceImpl<CoinWithdrawMapper, CoinWithdraw>
    implements CoinWithdrawService {

  @Autowired private UserServiceFeign userServiceFeign;

  @Override
  public Page<CoinWithdraw> findByPage(
      Page<CoinWithdraw> page,
      Long coinId,
      Long userId,
      String userName,
      String mobile,
      Byte status,
      String numMin,
      String numMax,
      String startTime,
      String endTime) {
    LambdaQueryWrapper<CoinWithdraw> coinWithdrawLambdaQueryWrapper = new LambdaQueryWrapper<>();
    // 1 若用户本次的查询中,带了用户的信息userId, userName,mobile ----> 本质就是要把用户的Id 放在我们的查询条件里面
    Map<Long, UserDto> basicUsers = null;
    if (userId != null
        || !StringUtils.isEmpty(userName) | !StringUtils.isEmpty(mobile)) { // 使用用户的信息查询
      // 需要远程调用查询用户的信息
      basicUsers =
          userServiceFeign.getBasicUsers(
              userId == null ? null : Arrays.asList(userId), userName, mobile);
      if (CollectionUtils.isEmpty(basicUsers)) { // 找不到这样的用户->
        return page;
      }
      Set<Long> userIds = basicUsers.keySet();
      coinWithdrawLambdaQueryWrapper.in(
          !CollectionUtils.isEmpty(userIds), CoinWithdraw::getUserId, userIds);
    }
    // 2 若用户本次的查询中,没有带了用户的信息
    coinWithdrawLambdaQueryWrapper
        .eq(coinId != null, CoinWithdraw::getCoinId, coinId)
        .eq(status != null, CoinWithdraw::getStatus, status)
        .between(
            !(StringUtils.isEmpty(numMin) || StringUtils.isEmpty(numMax)),
            CoinWithdraw::getNum,
            new BigDecimal(StringUtils.isEmpty(numMin) ? "0" : numMin),
            new BigDecimal(StringUtils.isEmpty(numMax) ? "0" : numMax))
        .between(
            !(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)),
            CoinWithdraw::getCreated,
            startTime,
            endTime + " 23:59:59");
    Page<CoinWithdraw> coinWithdrawPage = page(page, coinWithdrawLambdaQueryWrapper);
    List<CoinWithdraw> records = coinWithdrawPage.getRecords();
    if (!CollectionUtils.isEmpty(records)) {
      List<Long> userIds =
          records.stream().map(CoinWithdraw::getUserId).collect(Collectors.toList());
      if (CollectionUtils.isEmpty(basicUsers)) {
        basicUsers = userServiceFeign.getBasicUsers(userIds, null, null);
      }
      Map<Long, UserDto> finalBasicUsers = basicUsers;
      records.forEach(
          coinWithdraw -> { // 设置用户相关的数据
            UserDto userDto = finalBasicUsers.get(coinWithdraw.getUserId());
            if (userDto != null) {
              coinWithdraw.setUsername(userDto.getUsername()); // 远程调用查询用户的信息
              coinWithdraw.setRealName(userDto.getRealName());
            }
          });
    }
    return coinWithdrawPage;
  }
}