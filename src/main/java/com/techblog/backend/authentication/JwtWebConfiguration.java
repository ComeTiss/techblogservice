package com.techblog.backend.authentication;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class JwtWebConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired JwtAuthenticationProvider jwtAuthenticationProvider;
  @Autowired JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("**/graphql/**")
        .permitAll()
        .and()
        .exceptionHandling()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //        http.addFilterBefore(jwtAuthenticationTokenFilter,
    // UsernamePasswordAuthenticationFilter.class);
    http.headers().cacheControl();
  }
}
