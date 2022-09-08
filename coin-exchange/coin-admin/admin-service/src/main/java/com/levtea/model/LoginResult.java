package com.levtea.model;

import com.levtea.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登录结果")
public class LoginResult {
  @ApiModelProperty(value = "登录成功的token")
  private String token;

  @ApiModelProperty(value = "菜单数据")
  private List<SysMenu> menus;

  @ApiModelProperty(value = "权限数据")
  private List<SimpleGrantedAuthority> authorities;
}
