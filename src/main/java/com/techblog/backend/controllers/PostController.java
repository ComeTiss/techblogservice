package com.techblog.backend.controllers;

import com.techblog.backend.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/posts")
public class PostController {

    @Autowired
    GraphQLService graphQLService;

    public static class QueryData {
        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }

    @PostMapping
    public ResponseEntity<Object> postController(@RequestBody QueryData query){
        ExecutionResult executionResult = graphQLService.getGraphQL().execute(query.getQuery());
        if(!executionResult.getErrors().isEmpty()){
            return new ResponseEntity<>(executionResult.getErrors(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

}
