package com.levtea.config.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        // 由于使用的是JWT，我们这里不需要csrf
        .csrf()
        .disable()
        // 基于token，所以不需要session
        .sessionManagement()
        .disable()
        .authorizeRequests()
        .antMatchers(
            "/v2/api-docs",
            "/swagger-resources/configuration/ui", // 用来获取支持的动作
            "/swagger-resources", // 用来获取api-docs的URI
            "/swagger-resources/configuration/security", // 安全选项
            "/webjars/**",
            "/swagger-ui.html")
        .permitAll()
        .antMatchers("/**")
        .authenticated()
        .and()
        .headers()
        .cacheControl();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.tokenStore(jwtTokenStore());
  }

  private TokenStore jwtTokenStore() {
    JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
    return jwtTokenStore;
  }

  @Bean
  public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
    String s = null;
    try {
      ClassPathResource classPathResource = new ClassPathResource("coinexchange.pub");
      byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
      s = new String(bytes, "UTF-8");
    } catch (Exception e) {
      e.printStackTrace();
    }

    tokenConverter.setVerifierKey(s);
    return tokenConverter;
  }
}
