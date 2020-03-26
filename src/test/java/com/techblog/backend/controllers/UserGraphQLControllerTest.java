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
public class UserGraphQLControllerTest extends BaseTest {
  @Autowired private GraphQLController graphQLController;

  @Test
  public void testCreatePost() {
    BaseRequestData query = new BaseRequestData();
    query.setQuery(
        "mutation {\n"
            + "  authenticate(request: {email: \"test2@gmail.com\", authProvider: FACEBOOK}) {\n"
            + "    user {\n"
            + "      id\n"
            + "      email\n"
            + "    }\n"
            + "    token\n"
            + "  }\n"
            + "}");
    BaseResponse response = graphQLController.handleRequest(query);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
