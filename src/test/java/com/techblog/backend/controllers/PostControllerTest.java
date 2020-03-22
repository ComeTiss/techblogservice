package com.techblog.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.techblog.backend.BaseTest;
import com.techblog.backend.types.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class PostControllerTest extends BaseTest {
  @Autowired private PostController postController;

  @Test
  public void testCreatePost() {
    QueryData query = new QueryData();
    query.setQuery(
        "mutation {\n" +
                " mutatePost(request: { title: \"...\", description: \"...\" }) {\n" +
                "    post {\n" +
                "      id\n" +
                "      title\n" +
                "      createdAt\n" +
                "      updatedAt\n" +
                "    }\n" +
                "    error\n" +
                "    success\n" +
                "  }\n" +
                "}");
    BaseResponse response = postController.postController(query);
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testUpdatePost() {
    QueryData query = new QueryData();
    query.setQuery(
        "mutation {\n" +
                "    deletePostsByIds(ids: [60]) {\n" +
                "      success\n" +
                "      error\n" +
                "      posts {\n" +
                "        id\n" +
                "        title\n" +
                "        description\n" +
                "      }\n" +
                "    }\n" +
                "  }");
    BaseResponse response = postController.postController(query);
    System.out.println(response.getData());
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testFetchAllPost() {
    QueryData query = new QueryData();
    query.setQuery(
        "{\n" +
                "  getAllPosts {\n" +
                "    success\n" +
                "    error\n" +
                "    posts {\n" +
                "      id\n" +
                "      title\n" +
                "      description\n" +
                "    }\n" +
                "  }\n" +
                "}");
    BaseResponse response = postController.postController(query);
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
  }
}
