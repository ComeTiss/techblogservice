package com.techblog.backend.datafetchers.post;

import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AllPostDataFetcher implements DataFetcher<List<Post>> {

  @Autowired PostRepository postRepository;

  @Override
  public List<Post> get(DataFetchingEnvironment environment) {
    try {
      return postRepository.findAll();
    } catch (Exception e) {
      log.error("Post query failed: {}", e);
      throw e;
    }
  }
}
