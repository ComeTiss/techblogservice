package com.techblog.backend.authentication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public PasswordUtils() {
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
  }

  public String encode(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public boolean isValid(String passwordInput, String encodedPassword) {
    return bCryptPasswordEncoder.matches(passwordInput, encodedPassword);
  }
}
