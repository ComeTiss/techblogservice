package com.techblog.backend.types.error;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import java.util.ArrayList;
import java.util.List;

public class ServiceError implements GraphQLError {
  private final String message;
  private final List<SourceLocation> locations;
  private final ErrorClassification errorType;

  public ServiceError(Throwable t) {
    this.message = new ServiceErrorMessage(t.getMessage()).getMessage();
    this.locations = new ArrayList<>();
    this.errorType = null;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public List<SourceLocation> getLocations() {
    return this.locations;
  }

  @Override
  public ErrorClassification getErrorType() {
    return this.errorType;
  }
}
