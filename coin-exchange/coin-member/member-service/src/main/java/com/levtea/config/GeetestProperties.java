package com.levtea.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "geetest")
public class GeetestProperties {

  private String geetestId;
  private String geetestKey;
}
