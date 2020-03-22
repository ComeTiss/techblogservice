package com.techblog.backend.datafetchers;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.BaseTest;
import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostDataFetchersTest extends BaseTest {
  @Autowired PostRepository postRepository;
  @Autowired PostDataFetcher postDataFetcher;

  private final String TEST_DESCRIPTION = "POST DESCRIPTION";
  private final String TEST_DESCRIPTION2 = "POST DESCRIPTION_2";
  private final String TEST_TITLE = "POST TITLE";

  @Test
  public void testCreatePost() {
    createPost();
    List<Post> posts = postRepository.findAll();
    assertThat(posts.size()).isEqualTo(1);
    assertThat(posts.get(0).getTitle()).isEqualTo(TEST_TITLE);
    assertThat(posts.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION);
    assertThat(posts.get(0).getUpdatedAt()).isNull();
  }

  @Test
  public void testUpdatePost() {
    Post postCreated = createPost();
    assertThat(postRepository.findAll().size()).isEqualTo(1);
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = initQueryArguments();
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
    createPost();
    createPost();
    assertThat(postDataFetcher.getAllPosts(null).getPosts().size()).isEqualTo(2);
  }

  @Test
  public void testDeletePostsByIds() {
    Post postCreated = createPost();
    assertThat(postDataFetcher.getAllPosts(null).getPosts().size()).isEqualTo(1);
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    HashMap<String, Object> arguments = new HashMap<>();
    arguments.put("ids", ImmutableList.of(postCreated.getId().toString()));
    dataFetchingEnvironmentMock.setArguments(arguments);
    postDataFetcher.deletePostByIds(dataFetchingEnvironmentMock);
    assertThat(postDataFetcher.getAllPosts(null).getPosts().size()).isEqualTo(0);
  }

  private Post createPost() {
    return postDataFetcher.mutatePost(initQueryArguments()).getPost();
  }

  private DataFetchingEnvironmentMock initQueryArguments() {
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    Map<String, Object> arguments = new HashMap<>();
    LinkedHashMap<String, Object> post = new LinkedHashMap<>();
    post.put("title", TEST_TITLE);
    post.put("description", TEST_DESCRIPTION);
    arguments.put("request", post);
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
