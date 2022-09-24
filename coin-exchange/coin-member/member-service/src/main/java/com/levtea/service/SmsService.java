package com.levtea.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.Sms;

public interface SmsService extends IService<Sms> {

  boolean sendSms(Sms sms);
}
