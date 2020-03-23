package com.techblog.backend.types;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class BaseRequestData {
  @Getter @Setter private String query;
  @Getter @Setter private Map<String, Object> variables;

  public BaseRequestData() {
    this.variables = new HashMap<>();
  }
}
