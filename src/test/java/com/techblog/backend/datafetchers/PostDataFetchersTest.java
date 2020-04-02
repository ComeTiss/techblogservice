package com.techblog.backend.datafetchers;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.BaseTest;
import com.techblog.backend.model.Post;
import com.techblog.backend.model.User;
import com.techblog.backend.repository.PostRepository;
import com.techblog.backend.repository.UserRepository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostDataFetchersTest extends BaseTest {
  @Autowired PostRepository postRepository;
  @Autowired UserRepository userRepository;
  @Autowired PostDataFetcher postDataFetcher;

  private final String TEST_DESCRIPTION = "POST DESCRIPTION";
  private final String TEST_DESCRIPTION2 = "POST DESCRIPTION_2";
  private final String TEST_TITLE = "POST TITLE";

  @Test
  public void testCreatePost() {
    User user = createUser();
    createPost(user.getId());
    List<Post> posts = postRepository.findAll();
    assertThat(posts.size()).isEqualTo(1);
    assertThat(posts.get(0).getTitle()).isEqualTo(TEST_TITLE);
    assertThat(posts.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION);
    assertThat(posts.get(0).getUpdatedAt()).isNull();
  }

  @Test
  public void testUpdatePost() {
    User user = createUser();
    Post postCreated = createPost(user.getId());
    assertThat(postRepository.findAll().size()).isEqualTo(1);
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = initQueryArguments(user.getId());
    dataFetchingEnvironmentMock =
        updatePostInputMock(dataFetchingEnvironmentMock, "id", postCreated.getId().toString());
    postDataFetcher.mutatePost(dataFetchingEnvironmentMock);
    List<Post> posts = postRepository.findAll();
    assertThat(posts.size()).isEqualTo(1);
    assertThat(posts.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION);
    assertThat(posts.get(0).getUpdatedAt()).isNotNull();

    dataFetchingEnvironmentMock =
        updatePostInputMock(dataFetchingEnvironmentMock, "description", TEST_DESCRIPTION2);
    postDataFetcher.mutatePost(dataFetchingEnvironmentMock);
    List<Post> postsUpdated = postRepository.findAll();
    assertThat(postsUpdated.size()).isEqualTo(1);
    assertThat(postsUpdated.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION2);
  }

  @Test
  public void testFetchAllPost() {
    User user = createUser();
    createPost(user.getId());
    createPost(user.getId());
    assertThat(postDataFetcher.getPostsWithFilters(initFiltersArgument()).getPosts().size())
        .isEqualTo(2);
  }

  @Test
  public void testDeletePostsByIds() {
    User user = createUser();
    Post postCreated = createPost(user.getId());
    assertThat(postDataFetcher.getPostsWithFilters(initFiltersArgument()).getPosts().size())
        .isEqualTo(1);
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.put("ids", ImmutableList.of(postCreated.getId().toString()));
    dataFetchingEnvironmentMock.setArguments(arguments);
    postDataFetcher.deletePostByIds(dataFetchingEnvironmentMock);
    assertThat(postDataFetcher.getPostsWithFilters(initFiltersArgument()).getPosts().size())
        .isEqualTo(0);
  }

  private Post createPost(Long authorId) {
    return postDataFetcher.mutatePost(initQueryArguments(authorId)).getPost();
  }

  private User createUser() {
    return userRepository.save(new User("test@gmail.com", "password"));
  }

  private DataFetchingEnvironmentMock initQueryArguments(Long authorId) {
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    Map<String, Object> arguments = new HashMap<>();
    LinkedHashMap<String, Object> post = new LinkedHashMap<>();
    post.put("title", TEST_TITLE);
    post.put("description", TEST_DESCRIPTION);
    post.put("authorId", authorId.toString());
    arguments.put("request", post);
    dataFetchingEnvironmentMock.setArguments(arguments);
    return dataFetchingEnvironmentMock;
  }

  private DataFetchingEnvironmentMock initFiltersArgument() {
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    Map<String, Object> arguments = new HashMap<>();
    LinkedHashMap<String, Object> request = new LinkedHashMap<>();
    arguments.put("request", request);
    dataFetchingEnvironmentMock.setArguments(arguments);
    return dataFetchingEnvironmentMock;
  }

  private DataFetchingEnvironmentMock updatePostInputMock(
      DataFetchingEnvironmentMock inputMock, String field, Object value) {
    LinkedHashMap postInput = inputMock.getArgument("request");
    postInput.put(field, value);
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.put("request", postInput);
    DataFetchingEnvironmentMock updatedInputMock = new DataFetchingEnvironmentMock();
    updatedInputMock.setArguments(arguments);
    return updatedInputMock;
  }
}
