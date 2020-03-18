package com.techblog.backend.controllers;

import com.techblog.backend.service.PostService;
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
    PostService postService;

    public static class Data {
        private String query;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }
    }

    @PostMapping
    public ResponseEntity<Object> postController(@RequestBody Data query){
        ExecutionResult executionResult = postService.getGraphQL().execute(query.getQuery());
        if(!executionResult.getErrors().isEmpty()){
            return new ResponseEntity<>(executionResult.getErrors(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

}
