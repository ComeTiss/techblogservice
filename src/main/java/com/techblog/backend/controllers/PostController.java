package com.techblog.backend.controllers;

import com.techblog.backend.model.Post;
import com.techblog.backend.resolvers.PostResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostResolver postResolver;

    @RequestMapping(value = "/posts")
    public List<Post> getPosts() {
        return  postResolver.getPosts();
    }
}
