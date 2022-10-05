package com.levtea.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "Cash购买时的表单参数")
public class CashParam {
  @ApiModelProperty(value = "币种的ID")
  private Long coinId;

  @ApiModelProperty(value = "币种的数量")
  private BigDecimal num;

  @ApiModelProperty(value = "币种的金额")
  private BigDecimal mum;
}
