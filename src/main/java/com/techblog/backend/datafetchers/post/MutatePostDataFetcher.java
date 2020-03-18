package com.techblog.backend.datafetchers.post;

import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.GraphQLException;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MutatePostDataFetcher implements DataFetcher<Post> {

    @Autowired
    PostRepository postRepository;

    /**
     * Handles a Post mutation operation
     * - If the input contains an ID, the post will be UPDATED
     * - Else, the post will be CREATED
     * @param environment, contains query parameters
     * @return Post object mutated
     */
    @Override
    public Post get(DataFetchingEnvironment environment) {
        String postId = environment.getArgument("id");
        String title = environment.getArgument("title");
        String description = environment.getArgument("description");

        if (title.isEmpty() || description.isEmpty()) {
            throw new GraphQLException("Post title/description cannot be empty");
        }
        try {
            if (postId != null) {
                Post currentPost = postRepository.getOne(Long.valueOf(postId));
                currentPost.setDescription(description);
                currentPost.setTitle(title);
                return postRepository.save(currentPost);
            } else {
                return postRepository.save(new Post(title, description));
            }
        } catch(Exception e) {
            log.error("Post mutation failed: {}", e);
            throw e;
        }
    };
}
