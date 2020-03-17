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

    @PostMapping
    public ResponseEntity<Object> postController(@RequestBody String query){
        ExecutionResult executionResult = postService.getGraphQL().execute(query);

        if(!executionResult.getErrors().isEmpty()){
            return new ResponseEntity<>(executionResult.getErrors().get(0).getMessage(), HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

}
