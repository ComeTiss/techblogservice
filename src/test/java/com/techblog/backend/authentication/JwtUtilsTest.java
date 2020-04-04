package com.techblog.backend.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import com.techblog.backend.model.User;
import org.junit.jupiter.api.Test;

public class JwtUtilsTest {

  private final String USER_EMAIL = "test@gmail.com";
  private final String USER_PASSWORD = "password";

  @Test
  public void testJwtToken() {
    User user = new User(USER_EMAIL, USER_PASSWORD);
    String token = JwtUtils.generate(user);
    assertThat(token).isNotNull();

    User userFromToken = JwtUtils.validate(token);
    assertThat(userFromToken.getEmail()).isEqualTo(USER_EMAIL);
    assertThat(userFromToken.getPassword()).isEqualTo(USER_PASSWORD);
  }

  @Test
  public void testJwtValidate() {
    final String EMAIL = "test8@gmail.com";
    final String PASSWORD = "new password";
    final String TOKEN =
        "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0OEBnbWFpbC5jb20iLCJwYXNzd29y"
            + "ZCI6IiQyYSQxMCR3R1EyeUZtWDE0azdMd3RwemVlQ0wuZ2RDUktkSFhvWkZwZ0h6M3V3UFhhODJENzl4RHkzTyJ9."
            + "zFpKC6HlR4BdUwmtkYHhlSo03E1zNb2Ef6FrNH_I_Lqc4x7PzQgspqO5Cj83jNTjC6Z2s71kbzTsF7mq6eJJeg";

    User userFromToken = JwtUtils.validate(TOKEN);
    assertThat(userFromToken.getEmail()).isEqualTo(EMAIL);
    assertThat(PasswordUtils.isValid(PASSWORD, userFromToken.getPassword())).isTrue();
  }
}
