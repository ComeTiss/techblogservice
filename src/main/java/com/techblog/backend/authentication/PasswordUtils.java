package com.techblog.backend.authentication;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordUtils {

  private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public String encode(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public boolean isValid(String passwordInput, String encodedPassword) {
    return bCryptPasswordEncoder.matches(passwordInput, encodedPassword);
  }
}
