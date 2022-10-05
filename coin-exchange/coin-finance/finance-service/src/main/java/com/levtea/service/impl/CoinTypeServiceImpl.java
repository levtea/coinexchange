package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.CoinType;
import com.levtea.mapper.CoinTypeMapper;
import com.levtea.service.CoinTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CoinTypeServiceImpl extends ServiceImpl<CoinTypeMapper, CoinType>
    implements CoinTypeService {

  @Override
  public Page<CoinType> findByPage(Page<CoinType> page, String code) {
    return page(
        page,
        new LambdaQueryWrapper<CoinType>()
            .like(!StringUtils.isEmpty(code), CoinType::getCode, code));
  }

  @Override
  public List<CoinType> listByStatus(Byte status) {
    return list(new LambdaQueryWrapper<CoinType>().eq(status != null, CoinType::getStatus, status));
  }
}
