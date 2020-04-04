package com.techblog.backend.authentication;

import com.techblog.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class JwtUtils {

  public String generate(User user) {
    Claims claims = Jwts.claims().setSubject(user.getEmail());
    claims.put("password", user.getPassword());
    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
        .compact();
  }

  public User validate(String token) {
    User user = null;
    try {
      Claims body =
          Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
      user = new User();
      user.setEmail(body.getSubject());
      user.setPassword(body.get("password").toString());
    } catch (Exception e) {
      log.error("JwtValidator: failed to validate token with error: {}", e.getMessage());
    }
    return user;
  }
}
