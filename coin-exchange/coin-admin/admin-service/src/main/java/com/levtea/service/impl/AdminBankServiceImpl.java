package com.levtea.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.AdminBank;
import com.levtea.dto.AdminBankDto;
import com.levtea.mapper.AdminBankMapper;
import com.levtea.mappers.AdminBankDtoMappers;
import com.levtea.service.AdminBankService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank>
    implements AdminBankService {

  /**
   * 条件分页查询公司银行卡
   *
   * @param page 分页参数
   * @param bankCard 银行卡卡号
   * @return
   */
  @Override
  public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
    return page(
        page,
        new LambdaQueryWrapper<AdminBank>()
            .like(!StringUtils.isEmpty(bankCard), AdminBank::getBankCard, bankCard));
  }

  @Override
  public List<AdminBankDto> getAllAdminBanks() {
    List<AdminBank> adminBanks =
        list(new LambdaQueryWrapper<AdminBank>().eq(AdminBank::getStatus, 1));
    if (CollectionUtils.isEmpty(adminBanks)) {
      return Collections.emptyList();
    }
    List<AdminBankDto> adminBankDtos = AdminBankDtoMappers.INSTANCE.toConvertDto(adminBanks);
    return adminBankDtos;
  }
}
