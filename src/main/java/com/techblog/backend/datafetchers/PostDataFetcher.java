package com.techblog.backend.datafetchers;

import com.techblog.backend.dao.PostDao;
import com.techblog.backend.model.Post;
import com.techblog.backend.types.error.ServiceErrorMessage;
import com.techblog.backend.types.post.MutatePostResponse;
import com.techblog.backend.types.post.PostResponse;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostDataFetcher implements DataFetcher<List<Post>> {

  @Autowired PostDao postDao;

  public PostResponse getPostsWithFilters(DataFetchingEnvironment environment) {
    PostResponse response = new PostResponse();
    LinkedHashMap requestData = environment.getArgument("request");
    LinkedHashMap filters = (LinkedHashMap) requestData.get("filters");
    try {
      Optional<List<Post>> postsOpt = postDao.getPostsWithFilters(filters);
      postsOpt.ifPresent(response::setPosts);
    } catch (Exception e) {
      log.error("Post query failed: {}", e);
      response.setError(e.getMessage());
    }
    return response;
  }

  /**
   * Handles a Post mutation operation, a Post will be created (if no Id provied), else updated.
   *
   * @param environment, contains query parameters
   * @return Post object mutated
   */
  public MutatePostResponse mutatePost(DataFetchingEnvironment environment) {
    LinkedHashMap requestData = environment.getArgument("request");
    String title = requestData.get("title").toString();
    String description = requestData.get("description").toString();
    Object postId = requestData.get("id");
    MutatePostResponse response = new MutatePostResponse();

    if (title.isEmpty() || description.isEmpty()) {
      response.setError(
          new ServiceErrorMessage("Post title/description cannot be empty").getMessage());
      return response;
    }
    try {
      if (postId != null) {
        Post updatedPost = postDao.updatePost(requestData);
        response.setPost(updatedPost);
      } else {
        Optional<Post> newPost = postDao.createPost(requestData);
        if (!newPost.isPresent()) {
          response.setError(new ServiceErrorMessage("Invalid authorId provided").getMessage());
          return response;
        }
        response.setPost(newPost.get());
      }
    } catch (Exception e) {
      log.error("Post mutation failed: {}", e);
      response.setError(new ServiceErrorMessage(e.getMessage()).getMessage());
    }
    return response;
  }

  public PostResponse deletePostByIds(DataFetchingEnvironment environment) {
    List<String> requestIds = environment.getArgument("ids");
    List<Long> postIds = requestIds.stream().map(Long::valueOf).collect(Collectors.toList());
    PostResponse response = new PostResponse();
    try {
      Optional<List<Post>> postsDeletedOpt = postDao.deletePostsByIds(postIds);
      if (!postsDeletedOpt.isPresent()) {
        response.setError(
            new ServiceErrorMessage("entity ID doesn't match any record").getMessage());
        return response;
      }
      response.setPosts(postsDeletedOpt.get());
    } catch (Exception e) {
      log.error("Post mutation failed: {}", e);
      response.setError(e.getMessage());
    }
    return response;
  };

  @Override
  public List<Post> get(DataFetchingEnvironment environment) throws Exception {
    return null;
  }
}
