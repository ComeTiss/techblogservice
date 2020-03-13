package com.techblog.backend.datafetchers;

import com.techblog.backend.model.Post;
import com.techblog.backend.repository.PostRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

@Component
public class AllPostDataFetcher implements DataFetcher<List<Post>> {

    @Autowired
    PostRepository postRepository;

    @Override
    public List<Post> get(DataFetchingEnvironment environment) {
        return postRepository.findAll();
    };
}
