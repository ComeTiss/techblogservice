package com.techblog.backend.model;

import com.techblog.backend.types.user.AuthProvider;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String fullName;

  @Column(unique = true)
  private String email;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<PostVote> votes = new HashSet<>();

  private String password;
  private AuthProvider authProvider;

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
