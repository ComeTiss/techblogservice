package com.techblog.backend.datafetchers;

import com.google.common.collect.ImmutableList;
import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePostDataFetcher implements DataFetcher<Post> {

    @Autowired
    PostRepository postRepository;

    @Override
    public Post get(DataFetchingEnvironment environment) {
        try {
           String title = environment.getArgument("title");
           String description = environment.getArgument("description");
           Post post = new Post(title, description);
           return postRepository.save(post);

        } catch(Exception e) {
            return null;
        }
    };
}
