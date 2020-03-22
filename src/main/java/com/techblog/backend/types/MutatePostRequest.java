package com.techblog.backend.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class MutatePostRequest {
  @Getter @Setter private String id;
  @Getter @Setter private String title;
  @Getter @Setter private String description;
}
