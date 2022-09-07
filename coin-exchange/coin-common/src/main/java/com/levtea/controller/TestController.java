package com.levtea.controller;

import com.levtea.model.R;
import com.levtea.model.WebLog;
import com.levtea.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@Api(tags = "CoinCommon里面测试的接口")
public class TestController {

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Autowired private TestService testService;

  @GetMapping("/common/test")
  @ApiOperation(
      value = "测试方法",
      authorizations = {@Authorization("Authorization")})
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "param",
        value = "参数1",
        dataType = "String",
        paramType = "query",
        example = "paramValue"),
    @ApiImplicitParam(
        name = "param",
        value = "参数2",
        dataType = "String",
        paramType = "query",
        example = "paramValue")
  })
  public R<String> testMethod(String param, String param1) {
    return R.ok("ok");
  }

  @GetMapping("data/test")
  @ApiOperation(
      value = "日期格式化测试",
      authorizations = {@Authorization("Authorization")})
  public R<Date> testDate() {
    return R.ok(new Date());
  }

  @GetMapping("redis/test")
  @ApiOperation(
      value = "redis测试",
      authorizations = {@Authorization("Authorization")})
  public R<String> testRedis() {
    WebLog webLog = new WebLog();
    webLog.setResult("ok");
    webLog.setMethod("com.levtea.domain.WebLog.testRedis");
    webLog.setUsername("111000");
    redisTemplate.opsForValue().set("com.levtea.domain.WebLog", webLog);
    return R.ok("ok");
  }

  @GetMapping("jetcache/test")
  @ApiOperation(
      value = "jetcache测试",
      authorizations = {@Authorization("Authorization")})
  public R<String> testJetCache(String username) {
    WebLog webLog = testService.get(username);
    System.out.println(webLog);
    return R.ok("ok");
  }
}
