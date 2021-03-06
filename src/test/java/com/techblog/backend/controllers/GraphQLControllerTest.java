package com.techblog.backend.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.techblog.backend.BaseTest;
import com.techblog.backend.types.BaseRequestData;
import com.techblog.backend.types.BaseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class GraphQLControllerTest extends BaseTest {
  @Autowired private GraphQLController graphQLController;

  @Test
  public void testCreatePost() {
    BaseRequestData query = new BaseRequestData();
    query.setQuery(
        "mutation {\n"
            + " mutatePost(request: { title: \"...\", description: \"...\" }) {\n"
            + "    post {\n"
            + "      id\n"
            + "      title\n"
            + "      createdAt\n"
            + "      updatedAt\n"
            + "    }\n"
            + "    error\n"
            + "    success\n"
            + "  }\n"
            + "}");
    BaseResponse response = graphQLController.handleRequest(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testUpdatePost() {
    BaseRequestData query = new BaseRequestData();
    query.setQuery(
        "mutation {\n"
            + "    deletePostsByIds(ids: [60]) {\n"
            + "      success\n"
            + "      error\n"
            + "      posts {\n"
            + "        id\n"
            + "        title\n"
            + "        description\n"
            + "      }\n"
            + "    }\n"
            + "  }");
    BaseResponse response = graphQLController.handleRequest(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testFetchAllPost() {
    BaseRequestData query = new BaseRequestData();
    query.setQuery(
        "{\n"
            + "  getPostsWithFilters(request: {}) {\n"
            + "    success\n"
            + "    error\n"
            + "    posts {\n"
            + "      id\n"
            + "      title\n"
            + "      description\n"
            + "    }\n"
            + "  }\n"
            + "}");
    BaseResponse response = graphQLController.handleRequest(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
