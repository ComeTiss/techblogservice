package com.techblog.backend.resolvers;


import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired private PostRepository postRepository;


    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
