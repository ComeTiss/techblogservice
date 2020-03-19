package com.techblog.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.techblog.backend.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class PostControllerTest extends BaseTest {
  @Autowired private PostController postController;

  @Test
  public void testCreatePost() {
    QueryData query = new QueryData();
    query.setQuery(
        "mutation {\n"
            + "    deletePostsByIds(ids: [1, 2, 3]) {\n"
            + "      id\n"
            + "      title\n"
            + "      description\n"
            + "    }\n"
            + "  }");
    ResponseEntity response = postController.postController(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testUpdatePost() {
    QueryData query = new QueryData();
    query.setQuery(
        "mutation {\n"
            + "    deletePostsByIds(ids: [1, 2, 3]) {\n"
            + "      id\n"
            + "      title\n"
            + "      description\n"
            + "    }\n"
            + "  }");
    ResponseEntity response = postController.postController(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testFetchAllPost() {
    QueryData query = new QueryData();
    query.setQuery(
        "{\n"
            + "  getAllPosts {\n"
            + "    id\n"
            + "    title\n"
            + "    description\n"
            + "  }\n"
            + "}");
    ResponseEntity response = postController.postController(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
