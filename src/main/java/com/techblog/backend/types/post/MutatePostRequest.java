package com.techblog.backend.types.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MutatePostRequest {
   private String id;
   private String title;
   private String description;
}
