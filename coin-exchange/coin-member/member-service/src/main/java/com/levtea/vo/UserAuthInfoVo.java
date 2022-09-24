package com.levtea.vo;

import com.levtea.domain.User;
import com.levtea.domain.UserAuthAuditRecord;
import com.levtea.domain.UserAuthInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "用户认证详细信息")
public class UserAuthInfoVo implements Serializable {

  @ApiModelProperty(value = "用户")
  private User user;

  @ApiModelProperty(value = "用户认证的详情列表")
  private List<UserAuthInfo> userAuthInfoList;

  @ApiModelProperty(value = "用户审核历史")
  private List<UserAuthAuditRecord> authAuditRecordList;
}
