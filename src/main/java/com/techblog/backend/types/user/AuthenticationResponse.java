package com.techblog.backend.types.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
  private BasicUser user;
  private String token;
  private String error;
}
