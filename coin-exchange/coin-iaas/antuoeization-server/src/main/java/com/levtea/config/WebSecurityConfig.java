package com.levtea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) {
    // 用户密码
    String password = "qwer1234";
    // 创建密码加密的对象
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //    // 密码加密
    //    String newPassword = passwordEncoder.encode(password);
    //    System.out.println("加密后的密码为：" + newPassword);

    // 校验这两个密码是否是同一个密码
    // matches方法第一个参数是原密码，第二个参数是加密后的密码
    boolean matches =
        passwordEncoder.matches(
            password, "$2a$10$XqsrGaGwG1AwrYcUUhxI9.C7jlHhfdKqw6rnD6x6rAehjTLowId1i");
    System.out.println("两个密码一致:" + matches);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http.authorizeRequests().anyRequest().authenticated();
  }

  //    @Bean
  //    public UserDetailsService userDetailsService() {
  //        InMemoryUserDetailsManager inMemoryUserDetailsManager = new
  // InMemoryUserDetailsManager();
  //        User user = new User("admin", "123456", Arrays.asList(new
  // SimpleGrantedAuthority("Role_Admin")));
  //        inMemoryUserDetailsManager.createUser(user);
  //        return inMemoryUserDetailsManager;
  //    }

  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  /**
   * 密码加密
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
