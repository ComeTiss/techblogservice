package com.techblog.backend.types.post;

import com.techblog.backend.model.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MutatePostResponse {
  private Boolean success;
  private String error;
  private Post post;

  public MutatePostResponse() {
    this.success = false;
  }
}
