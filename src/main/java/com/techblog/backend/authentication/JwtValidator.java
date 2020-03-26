package com.techblog.backend.authentication;

import com.techblog.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtValidator {
  public User validate(String token) {
    String KEY = "secret";
    User user = null;
    try {
      Claims body = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
      user = new User();
      user.setEmail(body.getSubject());
      user.setPassword(body.get("password").toString());
    } catch (Exception e) {
      log.error("JwtValidator: failed to validate token with error: {}", e.getMessage());
    }
    return user;
  }
}
