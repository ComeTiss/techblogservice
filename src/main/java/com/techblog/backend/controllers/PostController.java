package com.techblog.backend.controllers;

import com.techblog.backend.GraphQLService;
import com.techblog.backend.types.BaseResponse;
import com.techblog.backend.types.ServiceError;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/posts")
@Slf4j
public class PostController {

  @Autowired GraphQLService graphQLService;

  @PostMapping
  public BaseResponse postController(@RequestBody QueryData query) {
    try {
      ExecutionInput input =
          ExecutionInput.newExecutionInput()
              .query(query.getQuery())
              .variables(query.getVariables())
              .build();
      ExecutionResult executionResult = graphQLService.getGraphQL().execute(input);
      if (!executionResult.getErrors().isEmpty() || !executionResult.isDataPresent()) {
        return new BaseResponse(
            HttpStatus.BAD_REQUEST.value(),
            new ServiceError(executionResult.getErrors().get(0).getMessage()));
      }
      return new BaseResponse(HttpStatus.OK.value(), executionResult.getData());
    } catch (Exception e) {
      log.error(e.getMessage());
      return new BaseResponse(
          HttpStatus.INTERNAL_SERVER_ERROR.value(), new ServiceError("An error occurred"));
    }
  }
}
