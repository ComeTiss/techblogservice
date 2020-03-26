package com.techblog.backend.authentication;

import com.techblog.backend.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {
  public String generate(User user) {
    String KEY = "secret"; // TODO: generate real cryptographic keys
    Claims claims = Jwts.claims().setSubject(user.getEmail());
    claims.put("password", user.getPassword());
    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, KEY).compact();
  }
}
