package com.techblog.backend.authentication;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SecurityConstants {
  public static final String SECRET = "secret";
  public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/signup";
  public static final String LOGIN_URL = "/login";
  public static final String GRAPHQL_URL = "/graphql";
}
