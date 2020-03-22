package com.techblog.backend.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class BaseResponse {
  @Getter @Setter private int status;
  @Getter @Setter private Object data;
}
