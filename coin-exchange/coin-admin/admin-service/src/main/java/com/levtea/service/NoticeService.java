package com.levtea.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levtea.domain.Notice;

public interface NoticeService extends IService<Notice> {

  Page<Notice> findByPage(
      Page<Notice> page, String title, String startTime, String endTime, Integer status);

  Page<Notice> findNoticeForSimple(Page<Notice> page);
}
