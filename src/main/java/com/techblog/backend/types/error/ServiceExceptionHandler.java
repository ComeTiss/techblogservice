package com.techblog.backend.types.error;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

public class ServiceExceptionHandler implements DataFetcherExceptionHandler {

  @Override
  public DataFetcherExceptionHandlerResult onException(
      DataFetcherExceptionHandlerParameters handlerParameters) {
    GraphQLError error = new ServiceError(handlerParameters.getException());
    return new DataFetcherExceptionHandlerResult.Builder().error(error).build();
  }
}
