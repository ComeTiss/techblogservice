package com.techblog.backend.types.user;

import com.techblog.backend.model.User;
import lombok.Setter;

public class AuthenticationResponse {
  @Setter private User user;
  @Setter private String token;
  @Setter private String error;
}
