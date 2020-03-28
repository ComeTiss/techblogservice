package com.techblog.backend.controllers;

import com.techblog.backend.GraphQLService;
import com.techblog.backend.types.BaseRequestData;
import com.techblog.backend.types.BaseResponse;
import com.techblog.backend.types.error.ServiceError;
import com.techblog.backend.types.error.ServiceErrorMessage;
import com.techblog.backend.types.error.ServiceExceptionType;
import com.techblog.backend.types.post.BaseResponseData;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
@RequestMapping("/graphql")
@Slf4j
public class GraphQLController {

  @Autowired GraphQLService graphQLService;

  @PostMapping
  public BaseResponse handleRequest(@RequestBody BaseRequestData query) {
    try {
      ExecutionInput input =
          ExecutionInput.newExecutionInput()
              .query(query.getQuery())
              .variables(query.getVariables())
              .build();
      ExecutionResult executionResult = graphQLService.getGraphQL().execute(input);

      if (!executionResult.getErrors().isEmpty()) {
        ServiceError serviceError = (ServiceError) executionResult.getErrors().get(0);
        if (serviceError.getExceptionType().equals(ServiceExceptionType.AUTHENTICATION)) {
          return new BaseResponse(serviceError, HttpStatus.UNAUTHORIZED);
        }
        return new BaseResponse(serviceError, HttpStatus.BAD_REQUEST);
      }
      return new BaseResponse(new BaseResponseData(executionResult.getData()), HttpStatus.OK);
    } catch (Exception e) {
      log.error(e.getMessage());
      return new BaseResponse(
          new ServiceErrorMessage("An error occurred"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
