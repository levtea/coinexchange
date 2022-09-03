package com.levtea.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserDetailsService userDetailsService;

  //  @Autowired private RedisConnectionFactory redisConnectionFactory;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    clients
        .inMemory()
        .withClient("coin-api")
        .secret(passwordEncoder.encode("coin-secret"))
        .scopes("all")
        .accessTokenValiditySeconds(3600)
        .refreshTokenValiditySeconds(7 * 2600);
    super.configure(clients);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService)
        .tokenStore(jwtTokenStore())
        .tokenEnhancer(jwtAccessTokenConverter());
    super.configure(endpoints);
  }

  private TokenStore jwtTokenStore() {
    JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
    return jwtTokenStore;
  }

  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
    ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
    KeyStoreKeyFactory keyStoreKeyFactory =
        new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
    tokenConverter.setKeyPair(
        keyStoreKeyFactory.getKeyPair("coinexchange", "coinexchange".toCharArray()));
    return tokenConverter;
  }

  //  public TokenStore redisTokenStore() {
  //    return new RedisTokenStore(redisConnectionFactory);
  //  }
}
