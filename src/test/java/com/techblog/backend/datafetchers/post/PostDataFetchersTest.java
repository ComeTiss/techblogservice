package com.techblog.backend.datafetchers.post;

import static org.assertj.core.api.Assertions.assertThat;

import com.techblog.backend.BaseTest;
import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PostDataFetchersTest extends BaseTest {
  @Autowired PostRepository postRepository;
  @Autowired MutatePostDataFetcher mutatePostDataFetcher;
  @Autowired AllPostDataFetcher allPostDataFetcher;

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
  }

  @Test
  public void testUpdatePost() {
    Post postCreated = createPost();
    assertThat(postRepository.findAll().size()).isEqualTo(1);
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = initQueryArguments();
    dataFetchingEnvironmentMock.putArgument("id", postCreated.getId().toString());
    mutatePostDataFetcher.get(dataFetchingEnvironmentMock);
    List<Post> posts = postRepository.findAll();
    assertThat(posts.size()).isEqualTo(1);
    assertThat(posts.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION);

    dataFetchingEnvironmentMock.putArgument("description", TEST_DESCRIPTION2);
    mutatePostDataFetcher.get(dataFetchingEnvironmentMock);
    List<Post> postsUpdated = postRepository.findAll();
    assertThat(postsUpdated.size()).isEqualTo(1);
    assertThat(postsUpdated.get(0).getDescription()).isEqualTo(TEST_DESCRIPTION2);
  }

  @Test
  public void testFetchAllPost() {
    createPost();
    createPost();
    assertThat(allPostDataFetcher.get(null).size()).isEqualTo(2);
  }

  private Post createPost() {
    return mutatePostDataFetcher.get(initQueryArguments());
  }

  private DataFetchingEnvironmentMock initQueryArguments() {
    DataFetchingEnvironmentMock dataFetchingEnvironmentMock = new DataFetchingEnvironmentMock();
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("title", TEST_TITLE);
    arguments.put("description", TEST_DESCRIPTION);
    dataFetchingEnvironmentMock.setArguments(arguments);
    return dataFetchingEnvironmentMock;
  }
}
