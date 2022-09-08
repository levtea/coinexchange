package com.levtea.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.levtea.mapper")
public class MybatisPlusConfig {
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    paginationInterceptor.setDbType(DbType.MYSQL);
    return paginationInterceptor;
  }

  @Bean
  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
    OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
    return optimisticLockerInterceptor;
  }

  @Bean
  public IKeyGenerator iKeyGenerator() {
    H2KeyGenerator h2KeyGenerator = new H2KeyGenerator();
    return h2KeyGenerator;
  }
}
