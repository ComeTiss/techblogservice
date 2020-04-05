package com.techblog.backend.datafetchers;

import com.techblog.backend.model.User;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserDataFetcher implements DataFetcher<User> {

  @Override
  public User get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
