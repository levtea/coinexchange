package com.levtea.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levtea.mapper.WebConfigMapper;
import com.levtea.domain.WebConfig;
import com.levtea.service.WebConfigService;

@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

}

