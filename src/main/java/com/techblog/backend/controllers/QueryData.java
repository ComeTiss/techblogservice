package com.techblog.backend.controllers;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class QueryData {
  @Getter @Setter private String query;
  @Getter @Setter private Map<String, Object> variables;

  public QueryData() {
    this.variables = new HashMap<>();
  }
}
