package com.techblog.backend.datafetchers;

import com.techblog.backend.model.Post;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostRepository;
import com.techblog.backend.repository.UserRepository;
import com.techblog.backend.types.error.ServiceErrorMessage;
import com.techblog.backend.types.post.MutatePostResponse;
import com.techblog.backend.types.post.PostResponse;
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
  @Autowired UserRepository userRepository;

  public PostResponse getAllPosts(DataFetchingEnvironment environment) {
    PostResponse response = new PostResponse();
    try {
      response.setPosts(postRepository.findAll());
      response.setSuccess(true);
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
    Object authorId = requestData.get("authorId");
    Object postId = requestData.get("id");
    MutatePostResponse response = new MutatePostResponse();

    if (title.isEmpty() || description.isEmpty()) {
      response.setError(
          new ServiceErrorMessage("Post title/description cannot be empty").getMessage());
      return response;
    }
    try {
      if (postId != null) {
        Post currentPost = postRepository.getOne(Long.valueOf(postId.toString()));
        currentPost.setDescription(description);
        currentPost.setTitle(title);
        currentPost.setUpdatedAt(Instant.now());
        response.setPost(postRepository.save(currentPost));
      } else {
        if (authorId == null) {
          response.setError(new ServiceErrorMessage("authorID cannot be null").getMessage());
          return response;
        }
        User author = userRepository.getOne(Long.valueOf(authorId.toString()));
        if (author == null) {
          response.setError(new ServiceErrorMessage("Unknown authorID provided").getMessage());
          return response;
        }
        response.setPost(postRepository.save(new Post(title, description, author)));
      }
      response.setSuccess(true);
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
      List<Post> postsToDelete = postRepository.findAllById(postIds);
      if (postsToDelete.isEmpty()) {
        response.setSuccess(true);
        response.setError(
            new ServiceErrorMessage("entity ID doesn't match any record").getMessage());
        return response;
      }
      postRepository.deleteInBatch(postsToDelete);
      response.setSuccess(true);
      response.setPosts(postsToDelete);
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
