package com.levtea.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.levtea.service.impl")
public class JetCacheConfig {}
