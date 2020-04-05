package com.techblog.backend.types.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BasicUser {
  private Long id;
  private String email;
  private AuthProvider authProvider;
}
