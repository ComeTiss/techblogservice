package com.techblog.backend.authentication.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class EncryptionUtils {

  private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public String encodePassword(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public boolean isPasswordCorrect(String passwordInput, String encodedPassword) {
    return bCryptPasswordEncoder.matches(passwordInput, encodedPassword);
  }
}
