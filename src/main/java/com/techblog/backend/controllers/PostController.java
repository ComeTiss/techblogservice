package com.techblog.backend.controllers;

import com.techblog.backend.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/posts")
public class PostController {

  @Autowired GraphQLService graphQLService;

  @PostMapping
  public ResponseEntity<Object> postController(@RequestBody QueryData query) {
    try {
      ExecutionResult executionResult = graphQLService.getGraphQL().execute(query.getQuery());
      if (!executionResult.getErrors().isEmpty() || !executionResult.isDataPresent()) {
        return new ResponseEntity<>(
            executionResult.getErrors().get(0).getMessage(), HttpStatus.BAD_REQUEST);
      }
      return new ResponseEntity<>(executionResult, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
