package com.levtea.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.alicloud.sms.ISmsService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.domain.Sms;
import com.levtea.domin.Config;
import com.levtea.mapper.SmsMapper;
import com.levtea.service.ConfigService;
import com.levtea.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService {

  @Autowired private ISmsService smsService;

  @Autowired private ConfigService configService;

  @Autowired private StringRedisTemplate redisTemplate;

  @Override
  public boolean sendSms(Sms sms) {
    log.info("发送短信{}", JSON.toJSONString(sms, true));
    SendSmsRequest request = buildSmsRequest(sms);
    try {
      SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
      log.info("发送的结果为{}", JSON.toJSONString(sendSmsResponse, true));
      String code = sendSmsResponse.getCode();
      if ("OK".equals(code)) { // 发送成功,否则失败
        sms.setStatus(1);
        return save(sms);
      } else {
        return false;
      }
    } catch (ClientException e) {
      e.printStackTrace();
    }
    return false;
  }

  private SendSmsRequest buildSmsRequest(Sms sms) {
    SendSmsRequest sendSmsRequest = new SendSmsRequest();
    sendSmsRequest.setPhoneNumbers(sms.getMobile()); // 发送给谁

    Config signConfig = configService.getConfigByCode("SIGN");
    sendSmsRequest.setSignName(signConfig.getValue());

    Config configByCode = configService.getConfigByCode(sms.getTemplateCode());
    if (configByCode == null) {
      throw new IllegalArgumentException("输入的签名不存在");
    }
    sendSmsRequest.setTemplateCode(configByCode.getValue());

    String code = RandomUtil.randomNumbers(6);
    redisTemplate
        .opsForValue()
        .set("SMS:" + sms.getTemplateCode() + ":" + sms.getMobile(), code, 5, TimeUnit.MINUTES);
    sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
    sms.setContent("45678");
    String desc = configByCode.getDesc();
    String content = signConfig.getValue() + ":" + desc.replaceAll("\\$\\{code\\}", code);
    sms.setContent(content); // 最后短信的内容
    return sendSmsRequest;
  }
}
