package com.techblog.backend.authentication;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationTokenFilter {
  /*
  protected JwtAuthenticationTokenFilter() {

  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
      String header = request.getHeader("Authorisation");
      if (header == null || !header.startsWith("Bearer ")) {
          throw new RuntimeException(new ServiceError("JWT Token is missing").getMessage());
      }
    //  String authenticationToken = header.substring(7);
      return getAuthenticationManager().authenticate(null);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
      super.successfulAuthentication(request, response, chain, authResult);
  } */
}
