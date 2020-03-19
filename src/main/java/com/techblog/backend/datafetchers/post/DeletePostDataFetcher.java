package com.techblog.backend.datafetchers.post;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeletePostDataFetcher implements DataFetcher<List<Post>> {

  @Autowired PostRepository postRepository;

  /**
   * Handles a Post mutation operation - If the input contains an ID, the post will be UPDATED -
   * Else, the post will be CREATED
   *
   * @param environment, contains query parameters
   * @return Post object mutated
   */
  @Override
  public List<Post> get(DataFetchingEnvironment environment) {
    List<String> requestIds = environment.getArgument("ids");
    List<Long> postIds = requestIds.stream().map(Long::valueOf).collect(Collectors.toList());

    try {
      List<Post> postsToDelete = postRepository.findAllById(postIds);
      if (postsToDelete.isEmpty()) {
        return ImmutableList.of();
      }
      postRepository.deleteInBatch(postsToDelete);
      return postsToDelete;
    } catch (Exception e) {
      log.error("Post mutation failed: {}", e);
      throw e;
    }
  };
}
