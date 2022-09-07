package com.levtea.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.levtea.model.WebLog;
import com.levtea.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

  @Cached(
      name = "com.levtea.service.impl.TestServiceImpl:",
      key = "#username",
      cacheType = CacheType.BOTH)
  public WebLog get(String username) {
    WebLog webLog = new WebLog();
    webLog.setUsername(username);
    webLog.setResult("ok");
    return webLog;
  }
}
