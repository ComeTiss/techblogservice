package com.techblog.backend.datafetchers;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostDataFetcher implements DataFetcher<List<Post>> {

  @Autowired PostRepository postRepository;

  public List<Post> getAllPosts(DataFetchingEnvironment environment) {
    try {
      return postRepository.findAll();
    } catch (Exception e) {
      log.error("Post query failed: {}", e);
      throw e;
    }
  }

  /**
   * Handles a Post mutation operation, a Post will be created (if no Id provied), else updated.
   *
   * @param environment, contains query parameters
   * @return Post object mutated
   */
  public Post mutatePost(DataFetchingEnvironment environment) {
    LinkedHashMap postDataMap = environment.getArgument("post");
    String title = postDataMap.get("title").toString();
    String description = postDataMap.get("description").toString();

    if (title.isEmpty() || description.isEmpty()) {
      throw new GraphQLException("Post title/description cannot be empty");
    }
    try {
      if (postDataMap.get("id") != null) {
        Post currentPost = postRepository.getOne(Long.valueOf(postDataMap.get("id").toString()));
        currentPost.setDescription(description);
        currentPost.setTitle(title);
        currentPost.setUpdatedAt(Instant.now());
        return postRepository.save(currentPost);
      } else {
        return postRepository.save(new Post(title, description));
      }
    } catch (Exception e) {
      log.error("Post mutation failed: {}", e);
      throw e;
    }
  }

  public List<Post> deletePostByIds(DataFetchingEnvironment environment) {
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

  @Override
  public List<Post> get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
