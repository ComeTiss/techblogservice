package com.techblog.backend.types.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class BaseResponseData {
  @Getter private final Object data;
}
