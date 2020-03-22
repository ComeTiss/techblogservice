package com.techblog.backend.types;

import com.techblog.backend.model.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class PostResponse {
  @Getter @Setter private Boolean success;
  @Getter @Setter private String error;
  @Getter @Setter private List<Post> posts;

  public PostResponse() {
    this.success = false;
    this.posts = new ArrayList<>();
  }
}
