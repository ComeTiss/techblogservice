package com.techblog.backend.authentication.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CredentialsValidatorUtils {

  private static final String MUST_LOWER_CASE = "(?=.*[a-z])";
  private static final String MUST_DIGIT = "(?=.*\\d)";
  private static final String MUST_UPPER_CASE = "(?=.*[A-Z])";
  private static final String MUST_SYMBOL = "(?=.*[@#$%!])";
  private static final String MUST_LENGTH = ".{8,40}";

  private static final String PASSWORD_PATTERN =
      "(" + MUST_LOWER_CASE + MUST_DIGIT + MUST_UPPER_CASE + MUST_SYMBOL + MUST_LENGTH + ")";

  private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

  private Pattern patternPassword;
  private Pattern patternEmail;
  private Matcher matcher;

  public CredentialsValidatorUtils() {
    this.patternPassword = Pattern.compile(PASSWORD_PATTERN);
    this.patternEmail = Pattern.compile(EMAIL_PATTERN);
  }

  public boolean isPasswordValid(String password) {
    if (password == null || password.isEmpty()) {
      return false;
    }
    matcher = patternPassword.matcher(password);
    return matcher.matches();
  }

  public boolean isEmailValid(String email) {
    if (email == null || email.isEmpty()) {
      return false;
    }
    matcher = patternEmail.matcher(email);
    return matcher.matches();
  }

  public String invalidPasswordMessage() {
    return "Password must contain:\n"
        + "- 8 to 40 characters\n"
        + "- mix of Upper/Lower cases\n"
        + "- special characters\n"
        + "- one digit";
  }
}
