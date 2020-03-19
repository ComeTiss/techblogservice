package com.techblog.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
public class Post implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @Getter @Setter private String title;

  @Getter @Setter private String description;

  public Post() {}

  public Post(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
