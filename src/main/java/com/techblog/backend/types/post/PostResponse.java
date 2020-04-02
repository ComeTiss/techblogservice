package com.techblog.backend.types.post;

import com.techblog.backend.model.Post;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
  private Boolean success;
  private String error;
  private List<Post> posts;

  public PostResponse() {
    this.success = false;
    this.posts = new ArrayList<>();
  }
}
