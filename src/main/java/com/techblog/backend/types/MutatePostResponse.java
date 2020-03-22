package com.techblog.backend.types;

import com.techblog.backend.model.Post;
import lombok.Getter;
import lombok.Setter;

public class MutatePostResponse {
  @Getter @Setter private Boolean success;
  @Getter @Setter private String error;
  @Getter @Setter private Post post;

  public MutatePostResponse() {
    this.success = false;
  }
}
