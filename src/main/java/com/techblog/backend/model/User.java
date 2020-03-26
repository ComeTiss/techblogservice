package com.techblog.backend.model;

import com.techblog.backend.types.user.AuthProvider;
import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @Getter @Setter private String fullName;

  @Column(unique = true)
  @Getter
  @Setter
  private String email;

  @Getter @Setter private String password;
  @Getter @Setter private AuthProvider authProvider;

  public User() {}

  public User(String email) {
    this.email = email;
  }

  public User(String email, AuthProvider authProvider) {
    this.email = email;
    this.authProvider = authProvider;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
