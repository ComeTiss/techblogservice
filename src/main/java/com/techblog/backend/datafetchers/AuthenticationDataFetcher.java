package com.techblog.backend.datafetchers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class AuthenticationDataFetcher implements DataFetcher<Object> {

  /* Generate JWT token */

  /* Validate JWT token */

  /* Renew JWT token */

  @Override
  public Object get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
