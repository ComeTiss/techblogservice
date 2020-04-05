package com.techblog.backend.authentication.filters;

import com.techblog.backend.authentication.SecurityConstants;
import com.techblog.backend.authentication.utils.JwtUtils;
import com.techblog.backend.model.User;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  };

  /*
  @Override
  public Authentication attemptAuthentication(
          HttpServletRequest request,
          HttpServletResponse response
  ) {
     try {
         User user = new ObjectMapper()
                 .readValue(request.getInputStream(), User.class);

         return authenticationManager.authenticate(new );

     } catch(Exception e) {
         throw new RuntimeException(e);
     }
  } */

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    String token = JwtUtils.generate((User) authResult.getCredentials());
    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}
