package com.techblog.backend.authentication.filters;

import com.techblog.backend.authentication.SecurityConstants;
import com.techblog.backend.authentication.utils.JwtUtils;
import com.techblog.backend.model.User;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  public JwtAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }
    UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(request, response);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(
      HttpServletRequest request, HttpServletResponse response) {
    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header != null && header.length() >= 8) {
      String token = header.substring(7);
      User user = JwtUtils.validate(token);
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }
    }
    return null;
  }
}
